/** Created by flym at 13-3-12 */
package com.base.mybatis.page;

import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 提供自实现的oracle 分页拦截实现,以提供更简单的分页实现,自动计算总记录数
 *
 * @author flym
 */
@Intercepts({@Signature(type = Executor.class, method = "query",
		args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class OraclePageInterceptor implements Interceptor {
	private static final String START_INDEX = "startIndex_";
	private static final String END_INDEX = "endIndex_";
	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;
	static int ROWBOUNDS_INDEX = 2;
	static int RESULT_HANDLER_INDEX = 3;
	/** 不分页,或针对已经分页查询的数据 */
	private static final RowBounds NO_ROW_BOUNDS = RowBounds.DEFAULT;
	/** 是否已加载分页的resultMap */
	private boolean pageResultMapLoaed;

	private OracleDialect dialect = new OracleDialect();

	public Object intercept(Invocation invocation) throws Throwable {
		processIntercept((Executor) invocation.getTarget(), invocation.getArgs());
		return invocation.proceed();
	}

	void processIntercept(Executor executor, final Object[] queryArgs) throws SQLException {
		final RowBounds rowBounds = (RowBounds) queryArgs[ROWBOUNDS_INDEX];
		//只有提供分页信息时,才处理
		if(rowBounds == null)
			return;
		if(rowBounds.getLimit() == RowBounds.NO_ROW_LIMIT)
			return;
		boolean isPage = rowBounds instanceof Page;

		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object parameter = queryArgs[PARAMETER_INDEX];
		BoundSql oldBoundSql = ms.getBoundSql(parameter);
		String oldSql = oldBoundSql.getSql().trim();

		//处理总记录数
		if(isPage) {
			Page page = (Page) rowBounds;
			//只有需要查询总数的时候才查询,避免重复查询
			if(page.getTotalCount() == 0) {
				String countSql = getCountSql(oldSql);
				BoundSql countBoundSql = buildNewBoundSql(ms, oldBoundSql, countSql, NO_ROW_BOUNDS);
				//这时将要resultType设置为long类型,以取得总记录数信息
				ResultMap pageResultMap = fetchPageResultMap(ms.getConfiguration());
				MappedStatement countMappedStatement = buildNewStatement(ms, new BoundSqlSqlSource(countBoundSql),
						Collections.singletonList(pageResultMap));
				List<Long> list = executor
						.query(countMappedStatement, parameter, NO_ROW_BOUNDS, Executor.NO_RESULT_HANDLER);
				long totalCount = list.get(0);
				page.setTotalCount(totalCount);
			}
		}

		//避免结果集再处理一次
		queryArgs[ROWBOUNDS_INDEX] = NO_ROW_BOUNDS;

		//处理记录信息
		String pageSql = getPageSql(oldSql, rowBounds);
		BoundSql pageBoundSql = buildNewBoundSql(ms, oldBoundSql, pageSql, rowBounds);
		MappedStatement newMs = buildNewStatement(ms, new BoundSqlSqlSource(pageBoundSql), ms.getResultMaps());
		queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
	}

	private ResultMap fetchPageResultMap(Configuration configuration) {
		return configuration.getResultMap("cp._page");
	}

	private BoundSql buildNewBoundSql(MappedStatement ms, BoundSql boundSql, String sql, RowBounds rowBounds) {
		List<ParameterMapping> parameterMapping = boundSql.getParameterMappings();
		if(parameterMapping.isEmpty()) {
			parameterMapping = Lists.newArrayList();//避免出现不能添加参数的问题
		}
		BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, parameterMapping,
				boundSql.getParameterObject());
		for(ParameterMapping mapping : boundSql.getParameterMappings()) {
			String prop = mapping.getProperty();
			if(boundSql.hasAdditionalParameter(prop)) {
				newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
			}
		}

		//处理额外的分页参数
		if(rowBounds.getLimit() != RowBounds.NO_ROW_LIMIT) {
			parameterMapping = newBoundSql.getParameterMappings();
			if(rowBounds.getOffset() > 0) {
				//如果有起始点, 顺序为 <= end && > start
				parameterMapping.add(new ParameterMapping.Builder(ms.getConfiguration(), END_INDEX, int.class).build());
				parameterMapping
						.add(new ParameterMapping.Builder(ms.getConfiguration(), START_INDEX, int.class).build());
			} else {
				//没有起点 顺序为 <= end
				parameterMapping.add(new ParameterMapping.Builder(ms.getConfiguration(), END_INDEX, int.class).build());
			}

            /**分页页数等参数设置位置*/
            if (rowBounds.getOffset()==0){
                newBoundSql.setAdditionalParameter(END_INDEX, rowBounds.getLimit());
            }else {
                newBoundSql.setAdditionalParameter(START_INDEX, rowBounds.getLimit());//每页显示多少条
                newBoundSql.setAdditionalParameter(END_INDEX, rowBounds.getOffset());//从第几条开始
            }

        }
		return newBoundSql;
	}

	private MappedStatement buildNewStatement(MappedStatement ms, SqlSource newSqlSource, List<ResultMap> resultMapList) {
		final Configuration configuration = ms.getConfiguration();

		MappedStatement.Builder builder = new MappedStatement.Builder(configuration, ms.getId(), newSqlSource,
				ms.getSqlCommandType());

		builder.resource(ms.getResource());
		builder.fetchSize(ms.getFetchSize());
		builder.statementType(ms.getStatementType());
		builder.keyGenerator(ms.getKeyGenerator());
		builder.keyProperty(StringUtils.join(ms.getKeyProperties(), ","));
		builder.timeout(ms.getTimeout());
		builder.parameterMap(ms.getParameterMap());
		builder.resultMaps(resultMapList);
		builder.resultSetType(ms.getResultSetType());
		builder.cache(ms.getCache());
		builder.flushCacheRequired(ms.isFlushCacheRequired());
		builder.useCache(ms.isUseCache());

		return builder.build();
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
		//nothing to do
	}

	public static class BoundSqlSqlSource implements SqlSource {
		BoundSql boundSql;

		public BoundSqlSqlSource(BoundSql boundSql) {
			this.boundSql = boundSql;
		}

		public BoundSql getBoundSql(Object parameterObject) {
			return boundSql;
		}
	}

	private static String getCountSql(String sql) {
		return "select count(1) from (" + sql + ") t_ali";
	}

	private static String getPageSql(String sql, RowBounds page) {
		if(sql.toLowerCase().endsWith(" for update"))
			throw new RuntimeException("不支持for update操作");

		int startIndex = page.getOffset();

		StringBuffer select = new StringBuffer(sql.length() + 100);
		if(startIndex > 0) {
			//select.append("select * from ( select row_.*, rownum rownum_ from ( ");
			select.append(sql);
			select.append(" limit ?,? ");
		} else {
			//select.append("select * from ( ");
			select.append(sql);
			select.append(" limit ? ");
		}

		return select.toString();
	}
}
