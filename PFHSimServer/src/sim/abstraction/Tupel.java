package sim.abstraction;

/**
 * Generalization of tupels in wall-, and pfhouse-type.
 * One Tupel represents a kind of an attribute of the class "xxxxxType"
 * containing a type of resource needed for producing a specific wall/pfhouse
 * and the number of walls and/or resources required.
 * @author Alexander
 *
 * @param <T> must be a GeneralType
 */
public class Tupel<T extends GeneralType> {

	final public T type;
	final public int count;
	
	public Tupel(T type, int count) {
		this.type = type;
		this.count = count;
	}
	
}
