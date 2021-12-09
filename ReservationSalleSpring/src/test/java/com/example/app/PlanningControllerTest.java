package com.example.app;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanningControllerTest {

	private PlanningController controller;
	private List<Reservation> planning = new ArrayList<Reservation>();
	
	@org.junit.jupiter.api.BeforeEach
    void setUp() {
		controller = new PlanningController();
		planning = controller.createPlanning(controller.e.getLocaux(), controller.e.getReunions());
    }
	
	//Teste si les réservations respectent la condition de capacité
    @org.junit.jupiter.api.Test
    void testCapaciteRespectee() {
        boolean result = true;
        for(Reservation r : planning){
            if(r.getSalle()==null)
                continue;
            if(r.getReunion().getNbPersonnes()>r.getSalle().getCapacite()*0.7){
                result = false;
            }
        }
        assertTrue(result);
    }

    //Teste si les réservations respectent les conditions d'horaires
    @org.junit.jupiter.api.Test
    void testHorairesNonSuperposees() {
        boolean result = true;
        for(Reservation r : planning){
            if(r.getSalle()==null){

            }
            else if(planning.stream().anyMatch( x -> x!=r &&  r.getSalle() == x.getSalle() && ( r.getReunion().getCreneau()==x.getReunion().getCreneau() || r.getReunion().getCreneau()==x.getReunion().getCreneau()-1))){
                result = false;
            }
        }
        assertTrue(result);
    }

    //Teste si la quantité d'équipement réservée à chaque créneau est cohérente (inférieure ou égale à la quantité d'équipement libre)
    @org.junit.jupiter.api.Test
    void testQuantiteEquipementCoherent() {
        boolean result = true;
        int[] creneaux = {8,9,10,11};
        for(int c : creneaux){
            for(TypeEquipement te : TypeEquipement.values()){
                int total = 0;
                for(Reservation r : planning){
                    if(r.getSalle()==null)
                        continue;
                    if(r.getReunion().getCreneau() == c){
                        total += r.getEquipementReserve().get(te);
                    }
                }
                if(total>controller.e.getEquipementsLibres().get(te)){
                    result = false;
                }
            }
        }
        assertTrue(result);
    }

    //Teste si toutes les réunions sont dans la liste des réservations (même sans salle)
    @org.junit.jupiter.api.Test
    void testToutesReunionsTraitees() {
        boolean result = true;
        for(Reunion r : controller.e.getReunions()){
            result = false;
            for(Reservation res : planning){
                if(res.getReunion().getIdReunion()==r.getIdReunion()){
                    result = true;
                }
            }
            if(!result)
                break;
        }
        assertTrue(result);
    }

}
