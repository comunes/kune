package cc.kune.gspace.client.licensewizard;

import cc.kune.core.shared.dto.LicenseDTO;

public interface LicenseChooseCallback {

  void onSelected(LicenseDTO license);

}
