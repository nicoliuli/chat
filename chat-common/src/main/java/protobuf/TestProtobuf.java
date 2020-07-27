package protobuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestProtobuf {
    public static void main(String[] args) {

        // 构造person对象
        DataInfo.MyMessage personMsg = DataInfo.MyMessage.newBuilder()
                .setDataType(DataInfo.MyMessage.DataType.StudentType)
                .setPerson(DataInfo.Person.newBuilder()
                        .setAge(20).setName("张三").setSex("男")
                        .build())
                .build();

        DataInfo.Person person = DataInfo.Person.newBuilder().setAge(11).build();


        //构造student对象
        List<Integer> intList = new ArrayList<>();
        intList.add(1);intList.add(2);
        List<DataInfo.Person> personList = new ArrayList<>();
        Map<String,DataInfo.Person> personMap = new HashMap<>();
        personMap.put("p",person);

        personList.add(person);
        DataInfo.MyMessage studentMsg = DataInfo.MyMessage.newBuilder()
                .setDataType(DataInfo.MyMessage.DataType.StudentType)
                .setStudent(DataInfo.Student.newBuilder()
                        .setAge(18).setName("哈哈").setHeadAddr("baidu.com")
                        .addAllIntArr(intList) // 构建List<Integer>
                        .addAllPersonArr(personList) //构建List<Person>
                        .putAllPersonMap(personMap) //构建Map<String,Person>
                        .setLongNum(10L) // long
                        .build())
                .build();

        System.out.println(studentMsg.getStudent().getIntArrList().size());
        System.out.println(studentMsg.getStudent().getPersonArrList().size());
        System.out.println(studentMsg.getStudent().getPersonMapMap().size());
    }
}
