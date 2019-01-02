package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import model.dao.DaoFactory;
import model.dao.SignUpDao;
import model.entities.LogIn;
import model.entities.SignUp;

public class Program {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		SignUpDao signUpDao = DaoFactory.createSignUpDao();

		System.out.println("==== TEST 1: seller findById =======");
		SignUp signUp = signUpDao.findById(2);

		System.out.println(signUp);
	
	
		System.out.println("\n==== TEST 3: seller findAll =======");
		List<SignUp> list = new ArrayList<>();
		list = signUpDao.findAll();
		for (SignUp obj : list) {
			System.out.println(obj);
		}

		System.out.println("\n==== TEST 4: seller insert =======");
		LogIn logIn = new LogIn(1, null, null);
		SignUp newSignUp = new SignUp(null, "Greg","Michelini", "greg@gmail.com", 912334997, new Date(), 18,"M", logIn);
		signUpDao.insert(newSignUp);
		System.out.println("Inserted new id = " + newSignUp.getId());
	
		System.out.println("\n==== TEST 5: seller update =======");

		signUp = signUpDao.findById(1);
		signUp.setFirstName("Martha");
		signUpDao.update(signUp);
		System.out.println("Updated new name = " + signUp.getFirstName());

		/*
		System.out.println("\n==== TEST 6: seller delete =======");

		System.out.print("Enter id for delete test: ");
		int id = sc.nextInt();
		sellerDao.deleteById(id);
		System.out.println("Seller deleted!");*/
		sc.close();
	}

}
