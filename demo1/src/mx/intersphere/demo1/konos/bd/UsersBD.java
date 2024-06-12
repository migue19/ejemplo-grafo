package mx.intersphere.demo1.konos.bd;

import mx.intersphere.demo1.proteo.Demo1Graph;

public class UsersBD {
    Demo1Graph demo1Graph;
    public UsersBD(Demo1Graph demo1Graph){
        this.demo1Graph = demo1Graph;
    }

    public void loadData(){
        for (int i =0; i<10; i++){
            demo1Graph.create(demo1Graph.userStash("userId-"+i),"1").user("nombre"+i,"apellido"+i);
        }
    }
}
