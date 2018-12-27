package tes.sa.net.ibtakar.ibtakartest.ui.details;

import java.util.List;

import tes.sa.net.ibtakar.ibtakartest.data.network.models.Person;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Profile;
import tes.sa.net.ibtakar.ibtakartest.ui.base.MvpView;

public interface DetailsMvpView extends MvpView {

    void getData();
    void setImages(List<Profile> profiles);

    void setData(Person person);
}
