package model.people;

import model.disasters.Disaster;
import model.events.SOSListener;
import model.events.WorldListener;
import simulation.Address;
import simulation.Rescuable;
import simulation.Simulatable;

public class Citizen implements Simulatable,Rescuable{

//variables:
	
	private CitizenState state;
	private Disaster disaster;
	private Address location;
	private String nationalID;
	private String name;
	private int age;
	private int hp = 100;
	private int bloodLoss = 0;
	private int toxicity = 0;
	private WorldListener worldListener;
	private SOSListener emergencyService;
	
//setters/getters:
	
	public CitizenState getState() {
		return state;
	}
	
	public void setState(CitizenState state) {
		this.state = state;
	}
	
	public Disaster getDisaster() {
		return disaster;
	}
	
	public Address getLocation() {
		return location;
	}
	
	public void setLocation(Address location) {
		this.location = location;
	}
	
	public String getNationalID() {
		return nationalID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHp(int hp) {
		if(hp<=0) {
			this.hp = 0;
			state=CitizenState.DECEASED;
			return;
		}
		
		if(hp >= 100) {
			this.hp = 100;
			return;
		}
		
		this.hp = hp;
	}
	
	public int getBloodLoss() {
		return bloodLoss;
	}
	
	public void setBloodLoss(int bloodLoss) {
			
		if(bloodLoss <= 0) {
			this.bloodLoss = 0;
			return;
		}
			
		if(bloodLoss >=100) {
			this.setHp(0);
			this.bloodLoss = 100;
			return;
		}
			
		this.bloodLoss = bloodLoss;
	}
	
	
	public int getToxicity() {
		return toxicity;
	}
	
	public void setToxicity(int toxicity) {
			
		if(toxicity >=100) {
			this.setHp(0);
			this.toxicity = 100;
			return;
		}
			
		if(toxicity <= 0) {
			this.toxicity = 0;
			return;
		}
			
		this.toxicity = toxicity;
	}
	
	public WorldListener getWorldListener() {
		return worldListener;
	}

	public void setWorldListener(WorldListener worldListener) {
		this.worldListener = worldListener;
	}

	public void setEmergencyService(SOSListener emergencyService) {
		this.emergencyService = emergencyService;
	}
	
	
	//methods
	
	

	public void struckBy(Disaster d) {
		disaster = d;
		d.setActive(true);
		this.state = CitizenState.IN_TROUBLE;
		if(emergencyService!=null)
			emergencyService.receiveSOSCall(this);
	}
	public void cycleStep() {
		
		if ((bloodLoss>0 && bloodLoss<30))
			this.setHp(getHp()-5);
		
		if ((bloodLoss>=30 && bloodLoss<70))
			this.setHp(getHp()-10);
			
		if ((bloodLoss>=70 ))
			this.setHp(getHp()-15);
		
		if (( toxicity>0 && toxicity<30))
			this.setHp(getHp()-5);
		
		if ((toxicity>=30 && toxicity<70))
			this.setHp(getHp()-10);
			
		if ((toxicity>=70 ))
			this.setHp(getHp()-15);
			
		
		if((bloodLoss > 0 || toxicity > 0) && state != CitizenState.DECEASED) 
			this.state = CitizenState.IN_TROUBLE;
		
	}
	public String toString() {
		String s="This Citizen is located in : "+location.toString()+"\n"+
	"Name : "+name+"\n"+"Age : "+age+"\n"+"National ID : "+nationalID+"\n"+
				"Citizen's hp : "+hp+"\n"+"Citizen's blood loss : "+bloodLoss+"\n"+
	"Citizen's toxicity : "+toxicity+"\n"+"Citizen's state : "+state+"\n";
		if (this.getDisaster()!=null) 
			s+="Citizen's disaster : "+this.getDisaster().toString();
		return s;
	}
	public String occupant() {
		String s="Name : "+name+"\n"+"Age : "+age+"\n"+"National ID : "+nationalID+"\n"+
				"Citizen's hp : "+hp+"\n"+"Citizen's blood loss : "+bloodLoss+"\n"+
				"Citizen's toxicity : "+toxicity+"\n"+"Citizen's state : "+state+"\n";
		if (this.getDisaster()!=null) 
			s+="Citizen's disaster : "+this.getDisaster().toString()+"\n";
		s+="-----------------"+"\n";
		return s;
	}
	
	
//constructor(s):
	
	public Citizen(Address location, String nationalID, String name, int age,WorldListener listener) {
		worldListener = listener;
		this.location = location;
		if(worldListener!=null)
			worldListener.assignAddress(this, location.getX(), location.getY());
		this.nationalID = nationalID;
		this.name = name;
		this.age = age;
		state = CitizenState.SAFE;
	}
	
	public Citizen(Address location, String nationalID, String name, int age) {
		this.location = location;
		if(worldListener!=null)
			worldListener.assignAddress(this, location.getX(), location.getY());
		this.nationalID = nationalID;
		this.name = name;
		this.age = age;
		state = CitizenState.SAFE;
	}
	
	public Citizen() {}

	
	
}
