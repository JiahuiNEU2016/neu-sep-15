/**
 * 
 */
package project.vehicle.management.data.access;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import project.vehicle.management.data.Car;
import project.vehicle.management.data.Category;
import project.vehicle.management.data.Range;
import project.vehicle.management.data.SearchFilter;
import project.vehicle.management.data.SortCriteria;

/**
 * @author
 *
 */
public class CarManagerImpl implements CarManager {

	private List<Car> carList;
	private String dealerID;
	private File file;

	public String getDealerID() {
		return dealerID;
	}

	public void setDealerID(String dealerID) {
		this.dealerID = dealerID;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see project.vehicle.management.data.access.CarManager#listCars()
	 */

	private List<Car> buildCarList() throws IOException {
		List<Car> result = new ArrayList<Car>();
		FileInputStream fis = new FileInputStream(file);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String thisLine = null;
		br.readLine(); // exclude the first line
		while ((thisLine = br.readLine()) != null) {
			result.add(lineToCar(thisLine));
		}
		br.close();
		return result;
	}

	private List<Car> buildCarListOld() throws IOException {
		final List<Car> cars = new ArrayList<Car>();
		FileReadingTemplate template = new FileReadingTemplate() {
			@Override
			public void processLine(String line) {
				cars.add(lineToCar(line));
			}
		};
		template.parseFile(file);
		return cars;
	}

	private Car lineToCar(String s) {
		String[] l = s.split("~");
		String id = l[0];
		String dealerId = l[1];
		Category category = Category.valueOf(l[2].toUpperCase());
		Integer year = Integer.parseInt(l[3]);
		String make = l[4];
		String model = l[5];
		String trim = l[6];
		String type = l[7];
		Float price = Float.parseFloat(l[8]);
		return new Car(id, dealerId, category, year, make, model, trim, type,
				price);
	}

	public CarManagerImpl(String dealerID) throws IOException {
		this.setDealerID(dealerID);
		String filePath = dealerID;
		this.file = new File(filePath);
		this.carList = buildCarList();
	}

	@Override
	public List<Car> listCars() {
		return carList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.vehicle.management.data.access.CarManager#search(project.vehicle
	 * .management.data.SearchFilter)
	 */
	@Override
	public List<Car> search(SearchFilter sf) {
		List<Car> result = new ArrayList<Car>();
		for (int i=0;i<carList.size();i++) {
			if (checkSearchCondition(carList.get(i), sf))
				result.add(carList.get(i));
		}
		return result;
	}

	private boolean checkSearchCondition(Car car, SearchFilter sf) {
		if (!checkCondition(sf.getMake(),car.getMake()))
			return false;
		if (!checkCondition(sf.getModel(),car.getModel()))
			return false;
		if (!checkCondition(sf.getTrim(),car.getTrim()))
			return false;
		if (!checkCondition(sf.getYear(),car.getYear()+0.0f))
			return false;
		if (!checkCondition(sf.getRange(),car.getPrice()))
			return false;
		if (!checkCondition(sf.getCategory(),car.getCategory()))
			return false;
		if (!checkCondition(car,sf.getKeywords()))
			return false;
		return true;
	}
	private boolean checkCondition(Car car,String keywords){
		if(keywords==null||keywords.equals(""))
			return true;
		String words[] = keywords.split(" ");
		String carInfo = car.toString();
		for(String str : words){
			if(carInfo.indexOf(str)!=-1)
				return true;
		}
		return false;
	}
	private boolean checkCondition(boolean cc[],Category c){
		if((!cc[0])&&(!cc[1])&&(!cc[2]))
			return true;
		if(cc[0])
			if(c.equals(Category.NEW))
				return true;
		if(cc[1])
			if(c.equals(Category.USED))
				return true;
		if(cc[2])
			if(c.equals(Category.CERTIFIED))
				return true;
		return false;
	}
	private boolean checkCondition(String str1, String str2){
		if(str1!=null)
			if(!str1.equals(str2))
				return false;
		return true;
	}
	private boolean checkCondition(Range range, Float price){
		if (range != null) {
			if (range.getMax() != null)
				if(range.getMax() < price)
					return false;
			if (range.getMin() != null)
				if(range.getMin() > price)
					return false;
		}
		return true;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.vehicle.management.data.access.CarManager#addCar(project.vehicle
	 * .management.data.Car)
	 */
	@Override
	public void addCar(Car car) throws IOException {
		this.carList.add(car);
		addToFile(car.toString());
	}
	public void addToFile(String str) throws IOException{
		FileWriter fw = new FileWriter(file, true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.newLine();
		bw.write(str);
		bw.close();
		fw.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.vehicle.management.data.access.CarManager#deleteCar(java.lang
	 * .String)
	 */
	@Override
	public void deleteCar(String vehicleId) throws IOException {
		coverFile();
		for (int i=0;i<carList.size();) {
			if (carList.get(i).getID().equals(vehicleId))
				carList.remove(i);
			else
				addToFile(carList.get(i++).toString());
		}
	}
	private void coverFile() throws IOException{
		FileWriter fw = new FileWriter(file);
		fw.write("id~webId~category~year~make~model~trim~type~price");
		fw.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.vehicle.management.data.access.CarManager#updateCar(project.vehicle
	 * .management.data.Car)
	 */
	@Override
	public void updateCar(Car car) throws IOException {
		int count = 0;
		for(Car c : carList){
			if(c.getID().equals(car.getID())) break;
			count++;
		}
		carList.add(count, car);
		carList.remove(count+1);
		coverFile();
		for(Car c : carList)
			addToFile(c.toString());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * project.vehicle.management.data.access.CarManager#sort(project.vehicle
	 * .management.data.SearchFilter,
	 * project.vehicle.management.data.SortCriteria)
	 */
	@Override
	public List<Car> sort(SearchFilter sf, SortCriteria sc) {
		// override comparator.....
		//search satified data
		//carList.clear();
		carList = search(sf);
	    CarComparator ascComparator = new CarComparator();
        ascComparator.setAttribute(sc.getAttribute());
        Collections.sort(carList, ascComparator);
        if(!sc.getSequence()) {
            Comparator<Car> descComparator = Collections.reverseOrder(ascComparator); 
            Collections.sort(carList, descComparator); 
        }
        
        return carList;
	}

	@Override
	public List<String> setMake() {
		HashSet<String> makes = new HashSet<String>();
		for(Car car : carList){
            makes.add(car.getMake());
		}
		return new ArrayList<String>(makes);
	}

	@Override
	public List<String> setModel(String make) {
		HashSet<String> models = new HashSet<String>();
		for(Car car : carList){
            if(car.getMake().equals(make))
            	models.add(car.getModel());
		}
		return new ArrayList<String>(models);
	}

	@Override
	public List<String> setTrim(String model, String make) {
		HashSet<String> trims = new HashSet<String>();
		for(Car car : carList){
            if(car.getMake().equals(make))
            	if(car.getModel().equals(model))
            		trims.add(car.getTrim());
		}
		return new ArrayList<String>(trims);
	}
}
