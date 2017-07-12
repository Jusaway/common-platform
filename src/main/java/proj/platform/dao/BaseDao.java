package proj.platform.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseDao<T> {
	@Autowired
	private SessionFactory sessionFactory;
	private Class<T> persistentClass;	//T.class
	public static final Integer ASC_ORDER = 1;
	public static final Integer DESC_ORDER = -1;
	
	@SuppressWarnings("unchecked")
	protected Class<T> getPersistentClass(){
		if(persistentClass == null){
			Type type = getClass().getGenericSuperclass();
			persistentClass = (Class<T>) ((ParameterizedType)type).getActualTypeArguments()[0];
		}
		return persistentClass;
	}
    
	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	/**
	 * 保存
	 * @param entity
	 */
	public void save(T entity){
		getSession().save(entity);
	}
	/**
	 * 更新记录
	 * @param entity
	 */
	public void update(T entity){
		getSession().update(entity);
	}
	public void saveOrUpdate(T entity){
		getSession().saveOrUpdate(entity);
	}
	/**
	 * 删除记录
	 * @param entity
	 */
	public void delete(T entity){
		getSession().delete(entity);
	}
	/**
	 * 分页查询
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(Integer startRow, Integer pageSize, String orderName, Integer direction){
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		if(orderName != null && !orderName.isEmpty()){
			if(direction == ASC_ORDER){
				criteria.addOrder(Order.asc(orderName));
			}else if(direction == DESC_ORDER){
				criteria.addOrder(Order.desc(orderName));
			}
		}
		if(pageSize != -1){
			criteria.setFirstResult(startRow).setMaxResults(pageSize);
		}
		return criteria.list();
	}
	/**
	 * 根据单一条件查询
	 * @param propertyName
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T find(String propertyName, Object value){
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.add(Restrictions.eq(propertyName, value));
		return (T) criteria.uniqueResult();
	}
	/**
	 * 得到记录总数
	 * @return
	 */
	public Integer getTotalCount(){
		Criteria criteria = getSession().createCriteria(getPersistentClass());
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.uniqueResult()).intValue();
	}
}
