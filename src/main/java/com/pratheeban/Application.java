package com.pratheeban;

import java.io.IOException;

import com.pratheeban.pet.PetServer;
import com.pratheeban.server.DiscoveryServer;
import com.pratheeban.user.UserServer;
import com.pratheeban.web.WebServer;

public class Application {
	public static void main(String[] args) throws IOException {

		if (args.length == 0) {
			System.out.println("Specify application to start! (Options: reg, user, pet, web)");
		} else {
			switch (args[0]) {
			case "reg":
				DiscoveryServer.main(args);
				break;
			case "user":
				UserServer.main(args);
				break;
			case "pet":
				if (args.length == 2) {
					System.setProperty("server.port", args[1]);
				}
				PetServer.main(args);
				break;
			case "web":
				WebServer.main(args);
				break;
			default:
				System.out.println("Specify application to start! (Options:" + "reg, user, pet, web)");
			}
		}
	}

}
