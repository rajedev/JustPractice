package basics;

/**
 * Created by Rajendhiran Easu on 02/07/25.
 * Description:
 */

public class JTrails {

    String jName;
    int jAge;

    public String get(String name) {
        String result = "Hello from Java";
        if (name.equals("Rajendhiran")) {
            result = "Hello from " + name;
        }
        return result;
    }

    public void get(String name, int age) {
        System.out.println("Hello from Java with name: " + name + " and age: " + age);
    }

    public void get(int value) {
        System.out.println("Executing data with value: " + value);
    }

    public void get(JData dataModel) {
        System.out.println("Executing data with name: " + dataModel.name + " and age: " + dataModel.age);
    }

    public void set(String name, int age) {
        jName = name;
        jAge = age;
    }

    public String getName() {
        return jName;
    }

    public int getAge() {
        return jAge;
    }
}
