package sim.research.dev;

import java.util.ArrayList;
import java.util.List;

import sim.Enterprise;
import sim.ExceptionCategorie;
import sim.hr.Employee;
import sim.hr.HR;
import sim.production.Machine;
import sim.production.PFHouseType;
import sim.warehouse.Warehouse;

public class UpgradeProcessor {

	@SuppressWarnings("rawtypes")
	private List<Upgrade> upgradesInProg;
	
	public UpgradeProcessor() {
		upgradesInProg = new ArrayList<>();
	}

	@SuppressWarnings("rawtypes")
	public void processUpgrades(Enterprise e){
		for (int i = 0; i < upgradesInProg.size(); i++) {
			Upgrade u = upgradesInProg.get(i);
			u.simRound();
			
			if(u.isFinished()){
				upgradesInProg.remove(i--); //do not forget to reduce the index when removing an item ;)
				
				if(u instanceof ResearchProject){
					//not nice but easy
					e.getResearchedHouseTypes().add( ((ResearchProject)u).getReasearchType() );
				}
			}
		}
	}

	public EmployeeTraining startEmployeeTraining(Employee e) throws UpgradeException{
		if(e.isOnTraining() || !check(e))
			throw new UpgradeException(this, "Employee already on Training!", ExceptionCategorie.PROGRAMMING_ERROR);
		
		if(!e.canDoTraining())
			throw new UpgradeException(this, "Employee can not do Training!", ExceptionCategorie.PROGRAMMING_ERROR);
		
		EmployeeTraining t = new EmployeeTraining(e);
		t.setup();
		upgradesInProg.add(t);
		
		return t;
	}
	
	public MachineUpgrade startMachineUpgrade(Machine m, HR hr) throws UpgradeException{
		if(m.isInUpgrade() || !check(m))
			throw new UpgradeException(this, "Machine already in Upgrade!", ExceptionCategorie.PROGRAMMING_ERROR);
		
		if(!m.canDoUpgrade())
			throw new UpgradeException(this, "Machine can not do Upgrade!", ExceptionCategorie.PROGRAMMING_ERROR);
		
		MachineUpgrade up = new MachineUpgrade(m, hr);
		up.setup();
		upgradesInProg.add(up);
		
		return up;
	}

	public ExtendWarehouse startWarehouseExtension(Warehouse w) throws UpgradeException{
		if(!check(w))
			throw new UpgradeException(this, "Warehouse is already in Extension!", ExceptionCategorie.PROGRAMMING_ERROR);
		
		ExtendWarehouse ex = new ExtendWarehouse(w);
		ex.setup();
		upgradesInProg.add(ex);
		
		return ex;
	}

	@SuppressWarnings("rawtypes")
	public ResearchProject startResearchProject(PFHouseType type, Employee arch) throws UpgradeException{
		
		for (Upgrade u : upgradesInProg) {
			if(u instanceof ResearchProject)
				if(arch.equals(u.getUpgradeObject()) || type == ((ResearchProject)u).getReasearchType())
					throw new UpgradeException(this, "House Type already in Research!", ExceptionCategorie.PROGRAMMING_ERROR);
		}
		
		ResearchProject res = new ResearchProject(type, arch);
		res.setup();
		upgradesInProg.add(res);
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean check(Object o){
		for (Upgrade u : upgradesInProg) {
			if(o.equals(u.getUpgradeObject()))
				return false;
		}
		return true;
	}
}