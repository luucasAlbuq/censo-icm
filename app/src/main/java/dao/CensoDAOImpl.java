package dao;

import android.app.ActivityManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import icm.censo.a3_code.com.censoicm.CadastroActivity;
import icm.censo.a3_code.com.censoicm.MainActivity;
import model.Censo;

/**
 * Implementa as assinaturas para manipulacao de objetos @{@link Censo}
 * Created by luucasAlbuq on 30/05/2017.
 */

public class CensoDAOImpl implements CensoDAO {

    private static CensoDAO censoDAO;
    private static FirebaseDatabase firebaseDatabase;
    private static DatabaseReference db;
    public static Task task;


    private CensoDAOImpl(){};

    public static synchronized CensoDAO getInstance() {
        if(censoDAO == null){
            censoDAO = new CensoDAOImpl();
            db = getFirebaseDatabase().getReference("azenha");
        }
        return censoDAO;
    }

    public static FirebaseDatabase getFirebaseDatabase(){
        if(firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseDatabase.setPersistenceEnabled(true);
        }
        return firebaseDatabase;
    }


    @Override
    public <T> boolean save(final T object) {
        boolean isSaved = false;
        try{
            //Gerando Id
            String censoId = db.push().getKey();
            task = db.child(censoId).setValue(object);
            isSaved = task.isSuccessful();
        }catch (Exception e){
            Log.e("DB: save: ",e.getMessage());
        }finally {
            return isSaved;
        }
    }

    @Override
    public <T> boolean update(T object, T objectId) {
        //Permite salvar dados offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        return db.child((String) objectId).setValue(object).isSuccessful();
    }


    @Override
    public <T> boolean delete(T objectId) {
        //Permite salvar dados offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        return db.child((String) objectId).setValue(null).isSuccessful();
    }

    @Override
    public <T> Censo read(T objectId) {
        final Censo[] censo = new Censo[1];
        db.child((String) objectId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                censo[0] = dataSnapshot.getValue(Censo.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DB: read: ",databaseError.getMessage());
            }
        });
        return censo[0];
    }

    @Override
    public Set<Censo> getCensoFromTo(Date dataInicio, Date dataFim) {
        final Set<Censo> censoSet = new HashSet<>();
        Query query = db.orderByChild("data").startAt(dataInicio.toString()).endAt(dataFim.toString());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Censo censo = data.getValue(Censo.class);
                    censoSet.add(censo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DB: getCensoFromTo: ",databaseError.getMessage());
            }
        });
        return censoSet;
    }

    @Override
    public Censo getCensoByData(Date data) {
        final Censo[] censo = new Censo[1];
        Query query = db.orderByChild("data").equalTo(data.toString());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                censo[0] = dataSnapshot.getValue(Censo.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DB: getCensoByData: ",databaseError.getMessage());
            }
        });
        return censo[0];
    }

    @Override
    public Set<Censo> getCensoMes(int mes) {
        return null;
    }

    @Override
    public Set<Censo> getCensoAno(int ano) {
        return null;
    }
}
