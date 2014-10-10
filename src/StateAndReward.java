public class StateAndReward {

	private final static int NBR_STATES = 7;
	
	/* State discretization function for the angle controller */
	public static String getStateAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		String state = "OneStateToRuleThemAll";
		state = Integer.toString(discretize2(angle, NBR_STATES, -Math.PI, Math.PI));
		return state;
	}

	/* Reward function for the angle controller */
	public static double getRewardAngle(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		double reward = 0;
		int angleState = discretize(angle, NBR_STATES, -Math.PI, Math.PI);
		//System.out.println(NBR_STATES / 2);
		if(angleState == NBR_STATES/2)			
			reward = 1;
		else if(angleState < 2 || angleState > NBR_STATES-2)
			reward = -1;
		return reward;
	}

	/* State discretization function for the full hover controller */
	public static String getStateHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */

		String state = getStateAngle(angle, vx, vy);
		state += "-" + Integer.toString(discretize(vx, 3, -0.5, 0.5));
		state += "-" + Integer.toString(discretize(vy, 3, 0, 0.5));
		return state;
	}

	/* Reward function for the full hover controller */
	public static double getRewardHover(double angle, double vx, double vy) {

		/* TODO: IMPLEMENT THIS FUNCTION */
		
		double reward = 0;
		String test = ""+NBR_STATES/2+"-1-1";
		if(getStateHover(angle, vx, vy).substring(0, 1).equals(test.substring(0, 1)))			
			reward = 1;
		if(getStateHover(angle, vx, vy).substring(0, 3).equals(test.substring(0, 3)) && Integer.parseInt(getStateHover(angle, vx, vy).substring(4))<2)			
			reward = 2;	
		if(getStateHover(angle, vx, vy).equals(test))	
			reward = 5;
		/*if(!getStateHover(angle, vx, vy).substring(2, 3).equals("1"))
			reward = -1;*/
		if(Integer.parseInt(getStateAngle(angle, vx, vy))<1 || Integer.parseInt(getStateAngle(angle, vx, vy))>NBR_STATES-2)
			reward = -1;
		if(Integer.parseInt(getStateHover(angle, vx, vy).substring(4))==2)
			reward = -3;
		if(!getStateHover(angle, vx, vy).substring(2).equals("1") && !getStateHover(angle, vx, vy).substring(4).equals("1") && Integer.parseInt(getStateAngle(angle, vx, vy))<1 || Integer.parseInt(getStateAngle(angle, vx, vy))>NBR_STATES-2)
			reward = -6;
		return reward;
	}

	// ///////////////////////////////////////////////////////////
	// discretize() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 1 and nrValues-2 is returned.
	//
	// Use discretize2() if you want a discretization method that does
	// not handle values lower than min and higher than max.
	// ///////////////////////////////////////////////////////////
	public static int discretize(double value, int nrValues, double min,
			double max) {
		if (nrValues < 2) {
			return 0;
		}

		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * (nrValues - 2)) + 1;
	}

	// ///////////////////////////////////////////////////////////
	// discretize2() performs a uniform discretization of the
	// value parameter.
	// It returns an integer between 0 and nrValues-1.
	// The min and max parameters are used to specify the interval
	// for the discretization.
	// If the value is lower than min, 0 is returned
	// If the value is higher than min, nrValues-1 is returned
	// otherwise a value between 0 and nrValues-1 is returned.
	// ///////////////////////////////////////////////////////////
	public static int discretize2(double value, int nrValues, double min,
			double max) {
		double diff = max - min;

		if (value < min) {
			return 0;
		}
		if (value > max) {
			return nrValues - 1;
		}

		double tempValue = value - min;
		double ratio = tempValue / diff;

		return (int) (ratio * nrValues);
	}

}
