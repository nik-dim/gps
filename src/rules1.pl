findTaxi(Id,Flag) :-
	client(_,_,_,_,_,Persons,L_client,_),
	( Flag == 'true' -> taxi(_,_,Id,'true',Capacity,L_taxi,_,'true')
	; taxi(_,_,Id,'true',Capacity,L_taxi,_,_)
	),
	Capacity >= Persons,
	(L_taxi==L_client ; L_taxi=='both').

goodNeighbor(Current, Candidate) :-
	client(_,_,_,_,H,_,_,_),
	next(Current,Candidate),
	belongsTo(Candidate,L),
	( H>1800 -> line(L,_,_,yes,_,_,no,no,yes,_,no,_,_,no,no,_)
	; line(L,_,_,_,_,_,no,no,yes,_,no,_,_,no,no,_)	
	).
	

traffic_coeffient(Answer, P) :-
	client(_,_,_,_,Hour,_,_,_),
	belongsTo(P,L),
	( (Hour>=900 , 1100>=Hour) -> traffic(L,Slot,_,_)
	; (
		(Hour>=1500 , 1700>=Hour) -> traffic(L,_,Slot,_)
			; 
			(
				(Hour>=1900 , 2100>=Hour) -> traffic(L,_,_,Slot)
				; Slot = 'low'
			)
		)
	),
	(
		(Slot == 'medium') -> Answer is 1.2
		;
		(
			(Slot == 'high') -> Answer is 1.5
			; Answer is 1
		)
	).


maxspeed_coeffient(Answer, P) :-
	belongsTo(P,L),
	line(L,_,_,_,_,Maxspeed,_,_,_,_,_,_,_,_,_,_),
	Answer is 50/Maxspeed . 


	




