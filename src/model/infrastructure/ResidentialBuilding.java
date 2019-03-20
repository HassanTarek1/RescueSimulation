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
		if(structuralIntegrity <= 0) {
			this.KillAll();
			this.structuralIntegrity = 0;
			return;
		}
		
		this.structuralIntegrity = structuralIntegrity;
	}
	
	public int getFireDamage() {
		return fireDamage;
	}
	
	public void setFireDamage(int fireDamage) {
		if(fireDamage < 0) {
			this.fireDamage = 0;
			return;
		}
		
		if(fireDamage > 100) {
			this.fireDamage = 100;
			return;
		}
		
		this.fireDamage = fireDamage;
	}
	
	public int getGasLevel() {
		return gasLevel;
	}
	
	public void setGasLevel(int gasLevel) {
		if(gasLevel >= 100) {
			this.KillAll();
			this.gasLevel = 100;
			return;
		}
		if(gasLevel <= 0) {
			gasLevel = 0;
			return;
		}
		this.gasLevel = gasLevel;
	}
	
	public int getFoundationDamage() {
		return foundationDamage;
	}
	
	public void setFoundationDamage(int foundationDamage) {
		if(foundationDamage >= 100) {
			setStructuralIntegrity(0);
			this.foundationDamage = 100;
			return;
		}
		
		if(foundationDamage < 0) {
			this.foundationDamage = 0;
			return;
		}
		
		this.foundationDamage = foundationDamage;
	}
	
	public ArrayList<Citizen> getOccupants() {
		return occupants;
	}
	
	public Disaster getDisaster() {
		return disaster;
	}
	
	//methods: 
	
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
		
		if(fireDamage>=30 && fireDamage<70)
				this.setStructuralIntegrity(this.getStructuralIntegrity()-5);
			
		if(fireDamage>=70 && fireDamage<100)
				this.setStructuralIntegrity(this.getStructuralIntegrity()-7);
			
			
	}
	
	private void KillAll() {	
		for(Citizen currCitizen : this.occupants)
			currCitizen.setHp(0);
	}
	

//constructor(s):
	
	public ResidentialBuilding(Address location) {
		occupants = new ArrayList<>();
		this.location = location;
	}
	public ResidentialBuilding() {}

	
	
	
}
