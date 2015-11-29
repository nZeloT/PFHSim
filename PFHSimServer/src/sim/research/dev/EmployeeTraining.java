package sim.research.dev;

import sim.hr.Employee;
import sim.hr.Workplace;

public class EmployeeTraining extends Upgrade implements Workplace {
	
	private double skillIncrease;
	private int costIncrease;
	private Employee employee;
	
	private Workplace empWork;
	private boolean running;
	
	public EmployeeTraining(Employee e, int duration, int costs, double skillInc, int costInc) {
		super(duration, costs);
		this.employee = e;
		this.skillIncrease = skillInc;
		this.costIncrease = costInc;
	}

	@Override
	public void start() {
		empWork = employee.getWork();
		employee.assignWorkplace(this);
		running = true;
	}

	@Override
	protected void finish() {
		running = false;
		employee.assignWorkplace(empWork);
		employee.increaseSkill(skillIncrease);
		employee.increaseCosts(costIncrease);
	}

	@Override
	public boolean assignEmployee(Employee e) {
		return !running;
	}

	@Override
	public boolean unassignEmployee(Employee e) {
		//only allow reassignment of the employee when training is not running
		return !running;
	}
	
}
