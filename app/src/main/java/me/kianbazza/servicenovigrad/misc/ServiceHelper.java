package me.kianbazza.servicenovigrad.misc;

import android.os.Build;
import androidx.annotation.RequiresApi;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import me.kianbazza.servicenovigrad.services.Service;
import me.kianbazza.servicenovigrad.services.ServiceDocument;
import me.kianbazza.servicenovigrad.services.ServiceForm;

public class ServiceHelper {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean createService(Service service) {
        if ( isService(service) ) {
            return false;
        }

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference serviceNameRef = db.getReference("users/" + service.getName() + "/name");
        DatabaseReference servicePriceRef = db.getReference("users/" + service.getName() + "/price");
        DatabaseReference serviceFormRef = db.getReference("users/" + service.getName() + "/form");
        DatabaseReference serviceDocsRef = db.getReference("users/" + service.getName() + "/docs");

        serviceNameRef.setValue(service.getDisplayName());
        servicePriceRef.setValue(service.getPrice());

        ServiceForm form = service.getFormTemplate();
        form.getForm().forEach((field, value) -> serviceFormRef.child(field).setValue(value));

        ServiceDocument[] docs = service.getDocumentsTemplate();
        for (ServiceDocument document : docs) {
            serviceDocsRef.child(document.getName()).setValue(document.getLinkToDoc());
        }

        return true;

    }

    public boolean isService(Service service) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference servicesRef = db.getReference("services/");

        if (servicesRef.child(service.getName())==null) {
            return false;
        } else {
            return true;
        }
    }

}
