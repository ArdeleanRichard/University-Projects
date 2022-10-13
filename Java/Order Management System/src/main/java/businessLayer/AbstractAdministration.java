package businessLayer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataAccessLayer.ConnectionFactory;

public class AbstractAdministration<T> 
{
	protected static final Logger LOGGER = Logger.getLogger(AbstractAdministration.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractAdministration() 
	{
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	private String createSelectQuery(String field) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + field + " =?");
		return sb.toString();
	}

	public List<T> findAll() 
	{
		return null;
	}

	public T findById(int id) 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try 
		{
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} 
		catch (SQLException e) 
		{
			LOGGER.log(Level.WARNING, type.getName() + "Administration:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	
	private List<T> createObjects(ResultSet resultSet) 
	{
		List<T> list = new ArrayList<T>();

		try 
		{
			while (resultSet.next()) 
			{
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) 
				{
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} 
		catch (InstantiationException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} 
		catch (SecurityException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		catch (IntrospectionException e) 
		{
			e.printStackTrace();
		}
		return list;
	}

	private String createInsertQuery(String fields, int j) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT ");
		sb.append(" INTO ");
		sb.append(" customer ");
		sb.append(" ( "); 
		sb.append(" "+fields+" ");
		sb.append( " ) VALUES ( ");
		for(int i=0;i<j-1;i++)
			sb.append(" ?,");
		sb.append(" ?)");
		return sb.toString();
	}
	public T insert(T t) 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String str="";
		int nr=0;
		for(Field fields: t.getClass().getFields())
		{
			str=str+fields.getName()+",";
			nr++;
		}
		//str=str.substring(0, str.length()-1);
		
		String query = createInsertQuery(str,nr);
		System.out.println(query);
		try 
		{
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			int i=1;
			for(Field fields: t.getClass().getFields())
				try {
					statement.setObject(i++, fields.get(t));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} 
		catch (SQLException e) 
		{
			LOGGER.log(Level.WARNING, type.getName() + "Administration:insert " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return t;
	}

	private String createDeleteQuery(String fields) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append(" FROM ");
		sb.append(type.getSimpleName());
		sb.append(" WHERE " + fields + " =?");
		return sb.toString();
	}
	public T delete(int id) 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createDeleteQuery("id");
		try 
		{
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} 
		catch (SQLException e) 
		{
			LOGGER.log(Level.WARNING, type.getName() + "Administration:delete " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	
	private String createUpdateQuery(String fields, String id) 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(type.getSimpleName());
		sb.append(" SET ");
		sb.append(" "+fields+" ");
		sb.append( " WHERE "+id+"=? ");
		return sb.toString();
	}
	
	public T update(T t) 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String str="";
		for(Field fields: t.getClass().getFields())
		{
			str=str+fields.getName()+"=?,";
		}
		str=str.substring(0, str.length()-1);
		str=str+" ";
		String query = createUpdateQuery(str,"id");
		try 
		{
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			int i=1;
			for(Field fields: t.getClass().getFields())
				statement.setObject(i++, fields);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} 
		catch (SQLException e) 
		{
			LOGGER.log(Level.WARNING, type.getName() + "Administration:findById " + e.getMessage());
		} 
		finally 
		{
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return t; 
	}
}
