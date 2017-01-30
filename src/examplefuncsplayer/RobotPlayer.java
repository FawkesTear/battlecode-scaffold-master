package examplefuncsplayer;
import battlecode.common.*;

public strictfp class RobotPlayer {
    static RobotController rc;
	static int gardNum = 0;


    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
    **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        RobotPlayer.rc = rc;

        // Here, we've separated the controls into a different method for each RobotType.
        // You can add the missing ones or rewrite this into your own control structure.
        switch (rc.getType()) {
            case ARCHON:
                runArchon();
                break;
            case GARDENER:
                runGardener();
                break;
            case SOLDIER:
                runSoldier();
                break;
            case LUMBERJACK:
                runLumberjack();
                break;
        }
	}

     static void runArchon() throws GameActionException {
        System.out.println("I'm an archon!");
        initialArchonLocation = rc.getLocation();
        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                float archonStrideRadius = RobotType.ARCHON.strideRadius;
                 

                // Generate a random direction
                Direction dir = randomDirection();
                
                Direction upRight = new Direction((float) Math.PI/4);
                Direction upLeft = new Direction((float) (Math.PI/4)*3);
                Direction downLeft = new Direction((float) (Math.PI/4)*5);
                Direction downRight = new Direction((float) (Math.PI/4)*7);
                
                Direction up = Direction.NORTH;
                Direction right = Direction.EAST;
                Direction down = Direction.SOUTH;
                Direction left =  Direction.WEST;
                
                //sense trees
                TreeInfo[] avoidTree = rc.senseNearbyTrees(); //why is this wrong?
                float treeRadius = avoidTree[3].getRadius();
                MapLocation treeLoc = avoidTree[2].getLocation();
                MapLocation myLocation = rc.getLocation();
                float treeDistance = (float) Math.sqrt(Math.pow((treeLoc.x-myLocation.x), 2) + Math.pow((treeLoc.y-myLocation.y), 2));
                float minTreeDistance = (float) Math.sqrt(2)*treeRadius+2;
                
                //sense bullets
                BulletInfo[] bulletArray = rc.senseNearbyBullets();
                for(BulletInfo nearbyBullets: bulletArray){
                	 if (willCollideWithMe(nearbyBullets)==true){
                  	   rc.move(dir);
                     }
                }
                
               
                
             
                roundNumber = rc.getRoundNum();
                
                //donates all bullets at end, double check round number to make sure it is last round
                if (rc.getRoundNum() == GameConstants.GAME_DEFAULT_ROUNDS - 1){
                	rc.donate(rc.getTeamBullets());
                	
                }
                //Spawn Gardener initially or when there are no gardeners left
                else if (rc.getRoundNum() == 0 || gardNum == 0 && rc.canHireGardener(dir)){
                	rc.hireGardener(dir);
                	gardNum++;
                }
                // Randomly attempt to build a gardener in this direction
                else if (rc.canHireGardener(dir) && Math.random() < .01 && rc.getTeamBullets() > 300) {
                    rc.hireGardener(dir);
                    gardNum++;
                }
                
               
                
      //Archon Movement          
          //dodge bullets     
                
              
               
          //run into tree
               
               if (gotoCorner() == 0 && rc.canMove(up) && rc.canMove(right) && treeDistance >= minTreeDistance && treeDistance < minTreeDistance+archonStrideRadius){
            	   rc.move(up);   
               }
               else if (gotoCorner() == 1 && rc.canMove(up) && rc.canMove(left) && treeDistance >= minTreeDistance && treeDistance < minTreeDistance+archonStrideRadius){
            	   rc.move(up);
               }
               else if (gotoCorner() == 2 && rc.canMove(down) && rc.canMove(right) && treeDistance >= minTreeDistance && treeDistance < minTreeDistance+archonStrideRadius){
            	   rc.move(down);
               }
               else if (gotoCorner() == 3 && rc.canMove(down) && rc.canMove(left) && treeDistance >= minTreeDistance && treeDistance < minTreeDistance+archonStrideRadius){
            	   rc.move(down);
               }
               else if (gotoCorner() == 0 && rc.canMove(up)==false && rc.canMove(left) && treeDistance < minTreeDistance+archonStrideRadius){
            	   rc.move(left);   
               }
               else if (gotoCorner() == 1 && rc.canMove(up)==false && rc.canMove(right) && treeDistance < minTreeDistance+archonStrideRadius){
            	   rc.move(right);
               }
               else if (gotoCorner() == 2 && rc.canMove(down)==false && rc.canMove(left) && treeDistance < minTreeDistance+archonStrideRadius){
            	   rc.move(left);
               }
               else if (gotoCorner() == 3 && rc.canMove(down)==false && rc.canMove(right) && treeDistance < minTreeDistance+archonStrideRadius){
            	   rc.move(right);
               }
               
           //Archon move diagonally
   
               else if (gotoCorner() == 0 && rc.canMove(up) && rc.canMove(right)){
            	   rc.move(upRight);   
               }
               else if (gotoCorner() == 1 && rc.canMove(up) && rc.canMove(left)){
            	   rc.move(upLeft);
               }
               else if (gotoCorner() == 2 && rc.canMove(down) && rc.canMove(right)){
            	   rc.move(downRight);
               }
               else if (gotoCorner() == 3 && rc.canMove(down) && rc.canMove(left)){
            	   rc.move(downLeft);
               }
               
           
           //run into side
               
               else if (gotoCorner() == 0 && rc.canMove(up) && rc.canMove(right)==false){
            	   rc.move(up);
               }
               else if (gotoCorner() == 1 && rc.canMove(up) && rc.canMove(left)==false){
            	   rc.move(up);
               }
               else if (gotoCorner() == 2 && rc.canMove(down) && rc.canMove(right)==false){
            	   rc.move(down);
               }
               else if (gotoCorner() == 3 && rc.canMove(down) && rc.canMove(left)==false){
            	   rc.move(down);
               }
               else if (gotoCorner() == 0 && rc.canMove(up)==false && rc.canMove(right)){
            	   rc.move(right);
               }
               else if (gotoCorner() == 1 && rc.canMove(up)==false && rc.canMove(left)){
            	   rc.move(left);
               }
               else if (gotoCorner() == 2 && rc.canMove(down)==false && rc.canMove(right)){
            	   rc.move(right);
               }
               else if (gotoCorner() == 3 && rc.canMove(down)==false && rc.canMove(left)){
            	   rc.move(left);
               }
               
               
     
               else{
            	   
               }
                
                // Move randomly
                // Broadcast archon's location for other robots on the team to know
                rc.broadcast(0,(int)myLocation.x);
                rc.broadcast(1,(int)myLocation.y);
                
                
                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
    }
	static void runGardener() throws GameActionException {
        System.out.println("I'm a gardener!");

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

                // Listen for home archon's location
                int xPos = rc.readBroadcast(0);
                int yPos = rc.readBroadcast(1);
                MapLocation archonLoc = new MapLocation(xPos,yPos);

                // Generate a random direction
                Direction dir = randomDirection();

                // Randomly attempt to build a soldier or lumberjack in this direction
                if (rc.canBuildRobot(RobotType.SOLDIER, dir) && Math.random() < .01) {
                    rc.buildRobot(RobotType.SOLDIER, dir);
                } else if (rc.canBuildRobot(RobotType.LUMBERJACK, dir) && Math.random() < .01 && rc.isBuildReady()) {
                    rc.buildRobot(RobotType.LUMBERJACK, dir);
                }

                // Move randomly
                tryMove(randomDirection());

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
        }
    }

    static void runSoldier() throws GameActionException {
        System.out.println("I'm an soldier!");
        Team enemy = rc.getTeam().opponent();

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                MapLocation myLocation = rc.getLocation();

                // See if there are any nearby enemy robots
                RobotInfo[] robots = rc.senseNearbyRobots(-1, enemy);

                // If there are some...
                if (robots.length > 0) {
                    // And we have enough bullets, and haven't attacked yet this turn...
                    if (rc.canFireSingleShot()) {
                        // ...Then fire a bullet in the direction of the enemy.
                        rc.fireSingleShot(rc.getLocation().directionTo(robots[0].location));
                    }
                }

                // Move randomly
                tryMove(randomDirection());

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Soldier Exception");
                e.printStackTrace();
            }
        }
    }

    static void runLumberjack() throws GameActionException {
        System.out.println("I'm a lumberjack!");
        Team enemy = rc.getTeam().opponent();

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {

                // See if there are any enemy robots within striking range (distance 1 from lumberjack's radius)
                RobotInfo[] robots = rc.senseNearbyRobots(RobotType.LUMBERJACK.bodyRadius+GameConstants.LUMBERJACK_STRIKE_RADIUS, enemy);

                if(robots.length > 0 && !rc.hasAttacked()) {
                    // Use strike() to hit all nearby robots!
                    rc.strike();
                } else {
                    // No close robots, so search for robots within sight radius
                    robots = rc.senseNearbyRobots(-1,enemy);

                    // If there is a robot, move towards it
                    if(robots.length > 0) {
                        MapLocation myLocation = rc.getLocation();
                        MapLocation enemyLocation = robots[0].getLocation();
                        Direction toEnemy = myLocation.directionTo(enemyLocation);

                        tryMove(toEnemy);
                    } else {
                        // Move Randomly
                        tryMove(randomDirection());
                    }
                }

                // Clock.yield() makes the robot wait until the next turn, then it will perform this loop again
                Clock.yield();

            } catch (Exception e) {
                System.out.println("Lumberjack Exception");
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns a random Direction
     * @return a random Direction
     */
    static Direction randomDirection() {
        return new Direction((float)Math.random() * 2 * (float)Math.PI);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir,20,3);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles direction in the path.
     *
     * @param dir The intended direction of movement
     * @param degreeOffset Spacing between checked directions (degrees)
     * @param checksPerSide Number of extra directions checked on each side, if intended direction was unavailable
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {

        // First, try intended direction
        if (rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        boolean moved = false;
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }

    /**
     * A slightly more complicated example function, this returns true if the given bullet is on a collision
     * course with the current robot. Doesn't take into account objects between the bullet and this robot.
     *
     * @param bullet The bullet in question
     * @return True if the line of the bullet's path intersects with this robot's current position.
     */
    static boolean willCollideWithMe(BulletInfo bullet) {
        MapLocation myLocation = rc.getLocation();

        // Get relevant bullet information
        Direction propagationDirection = bullet.dir;
        MapLocation bulletLocation = bullet.location;

        // Calculate bullet relations to this robot
        Direction directionToRobot = bulletLocation.directionTo(myLocation);
        float distToRobot = bulletLocation.distanceTo(myLocation);
        float theta = propagationDirection.radiansBetween(directionToRobot);

        // If theta > 90 degrees, then the bullet is traveling away from us and we can break early
        if (Math.abs(theta) > Math.PI/2) {
            return false;
        }

        // distToRobot is our hypotenuse, theta is our angle, and we want to know this length of the opposite leg.
        // This is the distance of a line that goes from myLocation and intersects perpendicularly with propagationDirection.
        // This corresponds to the smallest radius circle centered at our location that would intersect with the
        // line that is the path of the bullet.
        float perpendicularDist = (float)Math.abs(distToRobot * Math.sin(theta)); // soh cah toa :)

        return (perpendicularDist <= rc.getType().bodyRadius);
    }
static int gotoCorner(){
    	float averageHeight = (GameConstants.MAP_MIN_HEIGHT + GameConstants.MAP_MAX_HEIGHT)/2;
    	float averageWidth = (GameConstants.MAP_MAX_HEIGHT + GameConstants.MAP_MIN_WIDTH)/2;
    	if(averageHeight-initialArchonLocation.y > .5*averageHeight ) {
    		if(averageWidth-initialArchonLocation.x > .5*averageWidth){
    			return 0;
    		}
    		else{
    			return 1;
    		}
    		
    		}

    	
    	else {
    		if(averageWidth-initialArchonLocation.x > .5*averageWidth){
    			return 2;
    		}
    		else {
    			return 3;
    		
    }
    		
}
    	
    	
}
}
