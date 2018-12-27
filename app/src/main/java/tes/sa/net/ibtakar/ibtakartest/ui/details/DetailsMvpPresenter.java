package tes.sa.net.ibtakar.ibtakartest.ui.details;

import java.util.List;

import tes.sa.net.ibtakar.ibtakartest.data.network.models.Person;
import tes.sa.net.ibtakar.ibtakartest.data.network.models.Profile;
import tes.sa.net.ibtakar.ibtakartest.ui.base.MvpPresenter;

public interface DetailsMvpPresenter<V extends DetailsMvpView> extends MvpPresenter<V> {

    void getData(int personId);

    void personImages(int personId);

    void setData(Person person);

    void setImages(List<Profile> profiles);
}
