package sim.warehouse;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import sim.abstraction.CostFactor;
import sim.hr.Employee;
import sim.hr.EmployeeType;
import sim.procurement.Resource;
import sim.procurement.ResourceType;
import sim.production.Wall;
import sim.production.WallType;

/**
 * This is the Warehouse of the enterprise.
 * Every Enterprise has exactly one warehouse. </br>
 * 
 * Use the warehouse to store resources and produced walls.
 * Employees of type STORE_KEEPER are required to operate the Warehouse.</br>
 * 
 * The capacity can be increased using Warehouse upgrades. Afterwards more employees may be required.
 * During warehouse upgrades the warehouse is still available.</br>
 * 
 * @author Leon
 */
public class Warehouse implements CostFactor{

	private int capacity;
	private int utilization;
	private int costs;

	private ArrayList<Employee> employees;

	private Warehouse.Storage<Wall, WallType> wallStore;
	private Warehouse.Storage<Resource, ResourceType> resStore;
	
	/**
	 * This creates a raw setup without any default values. All required values are requested.<br>
	 * The given employees are set to busy by the constructor.
	 * 
	 * @param capacity the initial capacity of the warehouse in volume units
	 * @param costs the initial costs of the warehouse e.g. rent and such; NOT the costs of the employees!
	 * @param emps the initially assigned employees of type STORE_KEEPER
	 * 
	 * @throws WarehouseException when the costs or capacity are below or equal to zero or the employees are not of type STORE_KEEPER
	 */
	public Warehouse(int capacity, int costs, Employee... emps) throws WarehouseException{
		
		this.capacity = capacity;
		this.costs = costs;
		
		this.wallStore = new Storage<>(Wall.class);
		this.resStore = new Storage<>(Resource.class);
		
		this.employees  = new ArrayList<Employee>();
		
		this.employees.addAll(Arrays.asList(emps));
		
		if(capacity <= 0)
			throw new WarehouseException("Capacity below zero is not allowed!");
		
		if(costs <= 0)
			throw new WarehouseException("Costs below zero are not allowed!");
		
		for (Employee e : emps) {
			if(e.getType() != EmployeeType.STORE_KEEPER)
				throw new WarehouseException("Only Employees of Type STORE_KEEPER can work in the Warehouse");
		}
		
		for (int i = 0; i < emps.length; i++) {
			emps[i].setFree(false);
		}
	}

	/**
	 * Store one or a bunch of walls in the warehouse.<br>
	 * They will only be stored if there is enough capacity for all given objects.
	 * @param w the wall/walls to be stored
	 * @return true if there was enough space and the walls are now stored; false otherwise
	 */
	public boolean storeWall(Wall... w){
		return wallStore.store(w);
	}

	/**
	 * Store one or a bunch of resources in the warehouse.<br>
	 * They will only be stored if there is enough capacity for all given objects.
	 * @param r the resource/resources to be stored
	 * @return true if there was enough space and the resources are now stored; false otherwise
	 */
	public boolean storeResource(Resource... r){
		return resStore.store(r);
	}

	/**
	 * remove exactly one wall of the given type from the warehouse
	 * @param type the walltype to be removed from the warehouse
	 * @return the concrete Wall object of the given type; or null if there was no such wall in the warehouse
	 */
	public Wall removeWall(WallType type){
		return wallStore.remove(type);
	}

	/**
	 * remove excatly the given amount of walls of the given type from the warehouse
	 * @param type the walltype to be removed from the warehouse
	 * @param count the amount of walls
	 * @return an array of walls of the given type with the given length; 
	 * or null in case no such walls are in the warehouse or not enough walls are stored of the given type 
	 * to fulfill the request
	 */
	public Wall[] removeWalls(WallType type, int count){
		return wallStore.remove(type, count);
	}

	/**
	 * remove exactly one resource of the given type from the warehouse
	 * @param type the resourcetype to be removed from the warehouse
	 * @return the concrete Resourve object of the given type; or null if there was no such resource in the warehouse
	 */
	public Resource removeResource(ResourceType type){
		return resStore.remove(type);
	}

	/**
	 * remove excatly the given amount of resources of the given type from the warehouse
	 * @param type the resourcetype to be removed from the warehouse
	 * @param count the amount of resources
	 * @return an array of resources of the given type with the given length; 
	 * or null in case no such resources are in the warehouse or not enough resources are stored of the given type 
	 * to fulfill the request
	 */
	public Resource[] removeResource(ResourceType type, int count){
		return resStore.remove(type, count);
	}

	/**
	 * check whether a wall of the given walltype is in the warehouse
	 * @param type the walltype to check
	 * @return true is there is such a wall
	 */
	public boolean isInStorage(WallType type){
		return isInStorage(type, 1);
	}

	/**
	 * check whether the given amount of walls of the given walltype are in the warehouse
	 * @param type the walltype to check
	 * @param count the amount of walls to go for
	 * @return true is there are enough walls
	 */
	public boolean isInStorage(WallType type, int count){
		return wallStore.isInStorage(type, count);
	}

	/**
	 * check whether a resource of the given resourcetype is in the warehouse
	 * @param type the resourcetype to check
	 * @return true is there is such a wall
	 */
	public boolean isInStorage(ResourceType type){
		return isInStorage(type, 1);
	}

	/**
	 * check whether the given amount of resources of the given resourcetype are in the warehouse
	 * @param type the resourcetype to check
	 * @param count the amount of resources to go for
	 * @return true is there are enough resources
	 */
	public boolean isInStorage(ResourceType type, int count){
		return resStore.isInStorage(type, count);
	}

	/**
	 * @return the amount of volume units currently in use
	 */
	public int getUtilization(){
		return utilization;
	}

	/**
	 * @return the maximum capacity of the warehouse
	 */
	public int getCapacity() {
		return capacity;
	}

	@Override
	public int getCosts() {
		return costs;
	}

	/**
	 * @return the complete costs of the warehouse
	 */
	public int getOverallCosts(){
		int costs = 0;
		for (int i = 0; i < employees.size(); i++) {
			costs = employees.get(i).getCosts();
		}

		return getCosts() + costs;
	}

	@Override
	public void setCosts(int costs) {
		this.costs = costs;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
//=====================================================================================
	/**
	 * A Storage for one StorageObject-Type e.g. Wall or Resource<br>
	 * This is for simplification and reduced code duplicates.<br>
	 * 
	 * @param <S> the StorageObject-Type
	 * @param <T> the StorageObjectType-Type e.g. WallType or ResourceType
	 */
	private class Storage<S extends StorageObject<T>, T>{
		private ArrayList<S> storage;
		private Class<S> storageObjectCls;
		
		public Storage(Class<S> storageObjectCls) {
			this.storage = new ArrayList<S>();
			this.storageObjectCls = storageObjectCls;
		}
		
		public boolean store(@SuppressWarnings("unchecked") S... s){
			int volume = 0;
			for (int i = 0; i < s.length; i++) {
				volume += s[i].getVolume();
			}

			//1. figure out how many space we need to store the resource
			if(utilization + volume > capacity)
				return false;

			//2. ok enough space. store it
			utilization += volume;
			storage.addAll(Arrays.asList(s));

			return true;
		}
		
		public S remove(T t){
			for (int i = 0; i < storage.size(); i++) {
				if(storage.get(i).getType() == t){
					utilization -= storage.get(i).getVolume();
					return storage.remove(i);
				}
			}
			
			return null;
		}
		
		public S[] remove(T t, int amount){
			//1. check if the ecessary count is avaliable
			if(!isInStorage(t, amount))
				return null;

			//2. remove the reqested amount from the storage
			@SuppressWarnings("unchecked")
			S[] ret = (S[]) Array.newInstance(storageObjectCls, amount);
			
			int c = 0;
			for (int i = 0; i < storage.size() && c < amount; i++) {
				if(storage.get(i).getType() == t){
					utilization -= storage.get(i).getVolume();
					ret[c++] = storage.remove(i--);
				}
			}

			return ret;
		}
		
		public boolean isInStorage(T t, int amount){
			int c = 0;
			for (int i = 0; i < storage.size() && c < amount; i++) {
				if(storage.get(i).getType() == t){
					c++;
				}
			}
			return c >= amount;
		}
	}

}