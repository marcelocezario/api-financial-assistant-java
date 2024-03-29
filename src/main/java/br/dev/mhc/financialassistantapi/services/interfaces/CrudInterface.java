package br.dev.mhc.financialassistantapi.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;

public interface CrudInterface<T, ID> {

	public T insert(T obj);

	public T update(T obj);

	public void delete(ID id);

	public T findById(ID id);

	public T findByUuid(String uuid);

	public List<T> findAll();

	public Page<T> findPage(Integer page, Integer linesPerPage, String orderBy, String direction);

}
