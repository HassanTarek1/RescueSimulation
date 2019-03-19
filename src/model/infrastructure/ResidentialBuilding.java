package model.infrastructure;

import java.util.ArrayList;
import java.util.Random;

import model.disasters.Disaster;
import model.disasters.Fire;
import model.events.SOSListener;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class ResidentialBuilding implements Simulatable,Rescuable{
	
//variables:
	
	private Address location;
	private int structuralIntegrity = 100;
	private int fireDamage = 0;
	private int gasLevel = 0;
	private int foundationDamage = 0;
	private ArrayList<Citizen> occupants;
	private Disaster disaster;
	private SOSListener emergencyService;
	
//setters/getters:
	
	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}

	public Address getLocation() {
		return location;
	}
	
	public int getStructuralIntegrity() {
		return structuralIntegrity;
	}
	
	public void setStructuralIntegrity(int structuralIntegrity) {
		this.structuralIntegrity = structuralIntegrity;
	}
	
	public int getFireDamage() {
		return fireDamage;
	}
	
	public void setFireDamage(int fireDamage) {
		this.fireDamage = fireDamage;
	}
	
	public int getGasLevel() {
		return gasLevel;
	}
	
	public void setGasLevel(int gasLevel) {
		this.gasLevel = gasLevel;
	}
	
	public int getFoundationDamage() {
		return foundationDamage;
	}
	
	public void setFoundationDamage(int foundationDamage) {
		this.foundationDamage = foundationDamage;
	}
	
	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}
	
	public Disaster getDisaster() {
		return disaster;
	}
	public void struckBy(Disaster d) {
		
	}
	public void cycleStep() {
		if (this.foundationDamage>0) {
			Random r=new Random();
			int rValue=(int)(6*r.nextDouble()+5);
			this.setStructuralIntegrity(this.getStructuralIntegrity()-rValue);
		}
		if(fireDamage>0 && fireDamage<30)
			this.setStructuralIntegrity(this.getStructuralIntegrity()-3);
		else {
			if(fireDamage>=30 && fireDamage<70)
				this.setStructuralIntegrity(this.getStructuralIntegrity()-5);
			else {
				if(fireDamage>=70 && fireDamage<100)
					this.setStructuralIntegrity(this.getStructuralIntegrity()-7);
			}
		}
			
	}
	

//constructor(s):
	
	public ResidentialBuilding(Address location) {
		occupants = new ArrayList<>();
		this.location = location;
	}
	public ResidentialBuilding() {}

	
	
	
}
