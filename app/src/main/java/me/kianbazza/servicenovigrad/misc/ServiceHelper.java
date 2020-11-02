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
    public void createService(Service service) {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference serviceNameRef = db.getReference("services/" + service.getName() + "/display-name");
        DatabaseReference servicePriceRef = db.getReference("services/" + service.getName() + "/price");
        DatabaseReference serviceFormRef = db.getReference("services/" + service.getName() + "/form");
        DatabaseReference serviceDocsRef = db.getReference("services/" + service.getName() + "/docs");

        serviceNameRef.setValue(service.getDisplayName());
        servicePriceRef.setValue(service.getPrice());

        ServiceForm form = service.getRequiredCustomerInfo();
        form.getInputFields().forEach((field, value) -> serviceFormRef.child(field).setValue(""));

        ServiceDocument[] docs = service.getRequiredDocuments();
        for (ServiceDocument document : docs) {
            serviceDocsRef.child(document.getName()).setValue("");
        }

    }

}
