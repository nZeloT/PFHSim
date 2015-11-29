package sim.research.dev;

import sim.hr.Employee;

public class EmployeeTraining extends Upgrade {
	
	private double skillIncrease;
	private int costIncrease;
	private Employee employee;
	
	public EmployeeTraining(Employee e, int duration, int costs, double skillInc, int costInc) {
		super(duration, costs);
		this.employee = e;
		this.skillIncrease = skillInc;
		this.costIncrease = costInc;
	}

	@Override
	public void start() {
		
	}

	@Override
	protected void finish() {
		employee.setFree(true);
		employee.increaseSkill(skillIncrease);
		employee.increaseCosts(costIncrease);
	}
	
}
