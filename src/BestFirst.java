import java.util.*;

/**
 * BestFirst class
 * Will solve the game board using an iterator and the State inner class
 */
public class BestFirst {
    protected Queue<State> abertos;
    protected HashSet<Ilayout> abertosHS;
    private Map<Ilayout, State> fechados;
    private State actual;
    private Ilayout objective;

    /**
     * State class
     * Saves a state of the board, saving it's layout, it's father state and the cost (g)
     */
    static class State{
        private Ilayout layout;
        private State father;
        private double g;

        public State(Ilayout l, State n){
            layout = l;
            father = n;
            if(father != null){
                g = father.g + l.getG();
            }
            else{
                g = 0.0;
            }
        }

        public String toString(){
            return layout.toString();
        }

        public double getG(){
            return g;
        }

        public int hashCode(){
            return toString().hashCode();
        }

        public boolean equals(Object o){
            if(o == null || this.getClass() != o.getClass()){
                return false;
            }
            State n = (State) o;
            return this.layout.equals(n.layout);
        }
    }

    /**
     * Uses the children method in Board class to find all the possible children
     * and only adds the ones who aren't going back to the previous step
     * @param n current state
     * @return list of successors
     */
    final private List<State> sucessores(State n){
        List<State> sucs = new ArrayList<>();
        List<Ilayout> children = n.layout.children();
        for(Ilayout e : children){
            if(n.father == null || !e.equals(n.father.layout)){
                State nn = new State(e, n);
                sucs.add(nn);
            }
        }
        return sucs;
    }

    /**
     * Method that solves the board
     * @param s initial board layout
     * @param goal board layout
     * @return iterator with all the steps taken to reach the goal in it
     */
    final public Iterator<State> solve(Ilayout s, Ilayout goal){
        objective = goal;
        abertos = new PriorityQueue<>(10, (s1,s2) -> (int) Math.signum(s1.getG()-s2.getG()));
        abertosHS = new HashSet<>();
        fechados = new HashMap<>();
        abertos.add(new State(s, null));
        List<State> sucs;

        //int k = 0;
        //In this loop we will solve the board
        while(true){
            //When abertos is empty it means there is no more things to test so we successfully failed
            if(abertos.isEmpty()){
                return null;
            }
            //k++;
            //takes and removes the first element in the priority queue
            actual = abertos.remove();

            //if we've reached our goal
            if(actual.layout.isGoal(objective)){
                //adding all of the steps we've made to reach here
                List<State> listOfSteps = new ArrayList<>();
                listOfSteps.add(actual);
                while(actual.father != null){
                    listOfSteps.add(actual.father);
                    actual = actual.father;
                }
                //reverses the list as we want to show it
                Collections.reverse(listOfSteps);
                //System.out.println(k);
                return listOfSteps.iterator();
            }
            //if we still haven't reached our goal
            else{
                //find the successors of the actual State
                sucs = sucessores(actual);
                //add the successors to the list of abertos
                for(State suc : sucs){
                    if(!fechados.containsKey(suc.layout) && !abertosHS.contains(suc.layout)){
                        abertos.add(suc);
                        abertosHS.add(suc.layout);
                    }
                }
                //add actual to the list of fechados
                fechados.put(actual.layout, actual);
            }
        }
    }
}
