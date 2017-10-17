package com.jt.common.mapper;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.DELETE_FROM;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Table;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.scripting.xmltags.MixedSqlNode;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.StaticTextSqlNode;

import com.github.abel533.mapper.MapperProvider;
import com.github.abel533.mapperhelper.EntityHelper;
import com.github.abel533.mapperhelper.MapperHelper;

public class SysMapperProvider extends MapperProvider {

    public SysMapperProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public SqlNode deleteByIDS(MappedStatement ms) {
        Class<?> entityClass = getSelectReturnType(ms);
        Set<EntityHelper.EntityColumn> entityColumns = EntityHelper.getPKColumns(entityClass);
        EntityHelper.EntityColumn column = null;
        for (EntityHelper.EntityColumn entityColumn : entityColumns) {
            column = entityColumn;
            break;
        }
        
        List<SqlNode> sqlNodes = new ArrayList<SqlNode>();
        // 开始拼sql
        BEGIN();
        // delete from table
        DELETE_FROM(tableName(entityClass));
        // 得到sql
        String sql = SQL();
        // 静态SQL部分
        sqlNodes.add(new StaticTextSqlNode(sql + " WHERE " + column.getColumn() + " IN "));
        // 构造foreach sql
        SqlNode foreach = new ForEachSqlNode(ms.getConfiguration(), new StaticTextSqlNode("#{"
                + column.getProperty() + "}"), "ids", "index", column.getProperty(), "(", ")", ",");
        sqlNodes.add(foreach);
        return new MixedSqlNode(sqlNodes);
    }      
    /**表示测试通用Mapper的代码
     * SqlNode其实就是将sql语句进行包装 交给mybatis 最终过解析SqlNode使Mysql执行操作
     * sql:  select count(*) from xxx
     * 如何获取表名?
     * 1获取当前执行的Mapper  itemMapper
     * 2获取ItemMapper继承SysMapper
     * 3获取SysMapper 中的对象(泛型)
     * 4.通过Item对象获取@Table注解
     * 5获取注解中的name属性
     * pojo对象的@Table含有name属性    name=tb_item
     * MappedStatement Mybatiss自己
     * @return
     */
    public SqlNode findMapperCount(MappedStatement ms){
    	String path = ms.getId();
    	//2获取ItemMapper路径
    	String mapperPath = path.substring(0, path.lastIndexOf("."));   	
    	try {
    		//通过反射获取
    		//3获取ItemMapper类型
			Class<?> targetClass = Class.forName(mapperPath);
			//4获取当前类型所继承的全部接口
			Type[] types = targetClass.getGenericInterfaces();
			//5获取SysMapper
			Type type = types[0];
			//判断当前type类型是否为泛型接口 ParameterizedType
			if(type instanceof ParameterizedType){
				//当前type为一个泛型
				//获取我的参数泛型类型
				ParameterizedType superType = (ParameterizedType)type;
				//7获取泛型的参数
				Type[] argsType = superType.getActualTypeArguments();
				//获取一个参数
				Class<?> targetArgsClass  = (Class<?>) argsType[0];
				//9获取参数的注解 判断是否含有@table注解
				if(targetArgsClass.isAnnotationPresent(Table.class)){
					Table table = targetArgsClass.getAnnotation(Table.class);
					String tableName = table.name();
					String sql = "select count(*) from "+tableName;
					SqlNode sqlNode = new StaticTextSqlNode(sql);
					return sqlNode;
				}				
			}										
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	return null;
    }    
}
