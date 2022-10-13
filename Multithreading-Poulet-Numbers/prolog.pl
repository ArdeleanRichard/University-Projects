isComposite(X):- X<4, !, false.
isComposite(X):- 0 is X mod 3, !.
isComposite(X):- 0 is X mod 2, !.
isComposite(X):- isComposite2(X, 5).

isComposite2(X, I):- I*I<X, 0 is X mod I, !. 
isComposite2(X, I):- I*I<X, I1 is I+2, 0 is X mod I1, !.
isComposite2(X, I):- I*I<X, I2 is I+6, isComposite2(X, I2).

isPoulet(P):- isComposite(P), !, 1 is mod(2**(P-1), P).



% N - interval max
% NW - number of workers
pouletNumbers(N, NW) :- 
	statistics(walltime, [Start|_]),
	create_workers(N, NW, Ids),
	join_workers(Ids),
	statistics(walltime, [Stop| [ExecutionTime]]),
	Runtime is Stop - Start,
	format('Time is: ~w ms or ~w ms', [Runtime, ExecutionTime]).

create_workers(N, NW, Ids):- create_workers_id(N, NW, NW, [], Ids).

% WN - worker number
% NW - number of workers
create_workers_id(_, 0, _, Ids, Ids):-!.
create_workers_id(N, WN, NW, Ids, Ids1) :-
	thread_create(worker(N, WN, NW), H, []),
	WN1 is WN-1,
	create_workers_id(N, WN1, NW, [H| Ids], Ids1).

join_workers([]):-!.
join_workers([H|T]) :-
	thread_join(H, S),
	join_workers(T).
	
worker(N, WN, NW) :-
	thread_self(Id),
	Acc is WN*2+1,
	do_work(Id, Acc, N, WN, NW).

do_work(_, Acc, N, _, _):- Acc>N, !.
do_work(Id, Acc, N, WN, NW):- isPoulet(Acc), !, 
	format('Thread (~w) found ~w\n', [WN, Acc]),
	Acc1 is Acc + NW*2, do_work(Id, Acc1, N, WN, NW).
do_work(Id, Acc, N, WN, NW):-  
	Acc1 is Acc + NW*2, do_work(Id, Acc1, N, WN, NW).
	
	