Ce petit projet implémente un algorithme de réservation de salle respectant certains critères.

Cet algorithme est implémenté dans la fonction PlanningController.createPlanning() et fonctionne comme suit :

On crée une structure Map<Reunion,List<Salle>> qui associe à chaque réunion la liste de toutes les salles disponibles,
l'objectif sera de réduire le nombre de salles possible pour chaque réunion selon les différents critères (capacité,
horaire et équipement).
On commence par retirer les salles ne respectant pas le critère de capacité puis on parcourt la liste en suivant
certaines règles :
    -1ère règle : Si une réunion n'a plus qu'une salle disponible, alors on la réserve*.
    -2ème règle : Si une réunion n'a plus de salle disponible alors on la réserve* avec un objet Salle
    initialisé à "null".
    -3ème règle : Si toutes les réunions ont 2 salles ou +, on traite en priorité les réunions ayant le moins de salle.
    -4ème règle : Pour choisir quelle salle réserver, on regarde celle dont il manque le moins d'équipement.
Après chaque itération :
    -On retire les réunions assignées de la Map.
    -On retire les salles réservées des liste de salle des réunions se déroulant sur le même créneau ou sur les créneaux
     adjacents.

*La réservation implique plusieurs étape :
    -On vérifie que la salle ne soit pas déjà utilisée sur les créneaux adjacents et qu'il y a assez d'équipements
    présent ou à emprunter.
    -On crée l'objet Reservation ayant comme attribut la Salle et la Reunion
    -On y ajoute les équipements manquant