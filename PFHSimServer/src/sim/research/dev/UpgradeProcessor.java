package sim.research.dev;

import java.util.ArrayList;
import java.util.List;

import sim.Enterprise;
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
			if(!u.isFinished())
				u.simRound();
			else{
				upgradesInProg.remove(i--); //do not forget to reduce the index when removing an item ;)
				
				

				if(u instanceof ResearchProject){
					//not nice but easy
					e.getResearchedHouseTypes().add( ((ResearchProject)u).getReasearchType() );
				}
			}
		}
	}

	public int startEmployeeTraining(Employee e){
		if(!check(e))
			return -1;
		
		EmployeeTraining t = new EmployeeTraining(e);
		t.setup();
		upgradesInProg.add(t);
		return t.getCosts();
	}

	public int startMachineUpgrade(Machine m, HR hr){
		if(!check(m))
			return -1;
		
		MachineUpgrade up = new MachineUpgrade(m, hr);
		up.setup();
		upgradesInProg.add(up);
		return up.getCosts();
	}

	public int startWarehouseExtension(Warehouse w){
		if(!check(w))
			return -1;
		
		ExtendWarehouse ex = new ExtendWarehouse(w);
		ex.setup();
		upgradesInProg.add(ex);
		return ex.getCosts();
	}

	@SuppressWarnings("rawtypes")
	public int startResearchProject(PFHouseType type, Employee arch){
		
		for (Upgrade u : upgradesInProg) {
			if(u instanceof ResearchProject)
				if(arch.equals(u.getUpgradeObject()) || type == ((ResearchProject)u).getReasearchType())
					return -1;
		}
		
		ResearchProject res = new ResearchProject(type, arch);
		res.setup();
		upgradesInProg.add(res);
		return res.getCosts();
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