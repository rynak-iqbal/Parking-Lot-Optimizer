public class CapacityOptimizer {
	private static final int NUM_RUNS = 10;

	private static final double THRESHOLD = 5.0;

	public static int getOptimalNumberOfSpots(int hourlyRate) {
		int n = 1;

		
		while (true){
			double totalQueueSize = 0;
			System.out.println("====Setting lot capacity to: " + n + "====");
			for (int m = 1; m <= NUM_RUNS; m++){
				ParkingLot lot = new ParkingLot(n);
				Simulator simulation = new Simulator(lot, hourlyRate, Simulator.SIMULATION_DURATION);
				long start = System.currentTimeMillis();
				simulation.simulate();
				long end = System.currentTimeMillis();
				long simulationTime = end - start;
				double incomingQueueSize = simulation.getIncomingQueueSize();
				System.out.println("Simulation run " + m + " (" + simulationTime + "ms); Queue length at the end of simulation run: " + incomingQueueSize);
				totalQueueSize += incomingQueueSize;
			}

			double averageQueueSize = totalQueueSize / (double) NUM_RUNS;


			if (averageQueueSize <= THRESHOLD) {
                return n;
            } else {
                n++;
			}

		}
	}

	public static void main(String args[]) {
	
		StudentInfo.display();

		long mainStart = System.currentTimeMillis();

		if (args.length < 1) {
			System.out.println("Usage: java CapacityOptimizer <hourly rate of arrival>");
			System.out.println("Example: java CapacityOptimizer 11");
			return;
		}

		if (!args[0].matches("\\d+")) {
			System.out.println("The hourly rate of arrival should be a positive integer!");
			return;
		}

		int hourlyRate = Integer.parseInt(args[0]);

		int lotSize = getOptimalNumberOfSpots(hourlyRate);

		System.out.println();
		System.out.println("SIMULATION IS COMPLETE!");
		System.out.println("The smallest number of parking spots required: " + lotSize);

		long mainEnd = System.currentTimeMillis();

		System.out.println("Total execution time: " + ((mainEnd - mainStart) / 1000f) + " seconds");

	}
}