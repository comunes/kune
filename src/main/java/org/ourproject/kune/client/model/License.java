/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 dated June, 1991.
 * 
 * This package is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 *
 */
package org.ourproject.kune.client.model;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

import java.util.ArrayList;
import java.util.Iterator;

import org.ourproject.kune.client.Img;

public class License extends Model implements IsSerializable, Cloneable {

	public final static int ALLOWMODIF = 1;

	public final static int ALLOWMODIFSHAREALIKE = 2;

	public final static int NOMODIF = 3;

	private String shortName;

	private String longName;

	private String description;

	private AbstractImagePrototype image;

	private boolean isCC = false;

	private boolean isCopyleft = false;

	private boolean isDeprecated = false; // TODO

	private String rdf;

	private String url;

	ArrayList licenseListeners = new ArrayList();

	public License() {
		// None license
		this.shortName = "none";
	}

	public void addLicenseListener(LicenseListener listener) {
		licenseListeners.add(listener);
	}

	public boolean allowCommercialUse() {
		if (isCC) {
			if ((shortName == "by") | (shortName == "by-sa")
					| (shortName == "by-nd")) {
				return true;
			} else {
				return false;
			}
		}
		throw new IllegalArgumentException("Illegal license");
	}

	public int allowModif() {
		if (isCC) {
			if ((shortName == "by-nd") | (shortName == "by-nc-nd")) {
				return License.NOMODIF;
			} else if ((shortName == "by-sa") | (shortName == "by-nc-sa")) {
				return License.ALLOWMODIFSHAREALIKE;
			} else if ((shortName == "by") | (shortName == "by-nc")) {
				return License.ALLOWMODIF;
			}
		}
		throw new IllegalArgumentException("Illegal license");
	}



	protected void fireLicenseListeners() {
		for (Iterator it = licenseListeners.iterator(); it.hasNext();) {
			((LicenseListener) it.next()).onLicenseChange(this);
		}
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the image
	 */
	public AbstractImagePrototype getImage() {
		return image;
	}

	/**
	 * @return the longName
	 */
	public String getLongName() {
		return longName;
	}

	/**
	 * @return the rdf
	 */
	public String getRdf() {
		return rdf;
	}

	/**
	 * @return the shortName
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the isCC
	 */
	public boolean isCC() {
		return isCC;
	}

	/**
	 * @return the isCopyleft
	 */
	public boolean isCopyleft() {
		return isCopyleft;
	}

	/**
	 * @return the isDeprecated
	 */
	public boolean isDeprecated() {
		return isDeprecated;
	}

	public boolean isNone() {
		// FIXME
		return (this.shortName == "none");
	}

	public void setCC(boolean allowCommercial, int allowModifShareAlike) {
		if (allowCommercial & (allowModifShareAlike == ALLOWMODIF)) {
			setLicense("by", "Creative Commons Attribution", "",
					"http://creativecommons.org/licenses/by/3.0/", "", true,
					false, false, Img.ref().ccby80x15());
		} else if (allowCommercial
				& (allowModifShareAlike == ALLOWMODIFSHAREALIKE)) {
			setLicense("by-sa", "Creative Commons Attribution-ShareAlike", "",
					"http://creativecommons.org/licenses/by-sa/3.0/", "", true,
					true, false, Img.ref().ccbysa80x15());
		} else if (allowCommercial & (allowModifShareAlike == NOMODIF)) {
			setLicense("by-nd", "Creative Commons Attribution-NoDerivs", "",
					"http://creativecommons.org/licenses/by-nd/3.0/", "", true,
					false, false, Img.ref().ccbynd80x15());
		} else if (!allowCommercial & (allowModifShareAlike == ALLOWMODIF)) {
			setLicense("by-nc", "Creative Commons Attribution-NonCommercial", "",
					"http://creativecommons.org/licenses/by-nc/3.0/", "", true,
					false, false, Img.ref().ccbync80x15());
		} else if (!allowCommercial
				& (allowModifShareAlike == ALLOWMODIFSHAREALIKE)) {
			setLicense("by-nc-sa", "Creative Commons Attribution-NonCommercial-ShareAlike", "",
					"http://creativecommons.org/licenses/by-nc-sa/3.0/", "",
					true, false, false, Img.ref().ccbyncsa80x15());
		} else if (!allowCommercial & (allowModifShareAlike == NOMODIF)) {

			setLicense("by-nc-nd", "Creative Commons Attribution-NonCommercial-NoDerivs", "",
					"http://creativecommons.org/licenses/by-nc-nd/3.0/", "",
					true, false, false, Img.ref().ccbyncnd80x15());
		}
	}

	/**
	 * @param shortName
	 *            Short name
	 * @param longName
	 *            Long name of the license
	 * @param description
	 *            Description of the license
	 * @param url
	 *            Url of the license explanation
	 * @param rdf
	 *            RDF info ot he license (if exists)
	 * @param isCC
	 *            If this license is a Creative Commons license
	 * @param isCopyleft
	 *            It this license is Copyleft
	 * @param isDeprecated
	 *            If the use of this license is deprecated
	 * @param image
	 *            Image of the license
	 */
	public void setLicense(String shortName, String longName,
			String description, String url, String rdf, boolean isCC,
			boolean isCopyleft, boolean isDeprecated,
			AbstractImagePrototype image) {
		this.shortName = shortName;
		this.longName = longName;
		this.description = description;
		this.url = url;
		this.rdf = rdf;
		this.isCC = isCC;
		this.isCopyleft = isCopyleft;
		this.isDeprecated = isDeprecated;
		this.image = image;
		fireLicenseListeners();
	}
	
	/**
	 * @return licenses not from Creative Commons
	 */
	public static License[] getLicensesNotCC() {
		// Fixme Get from repository
		License[] licenses = new License[1];
		licenses[0] = new License();
        licenses[0].setLicense("gfdl", "GNU Free Documentation License", "",
            "http://www.gnu.org/copyleft/fdl.html", "", false, true, false,
            Img.ref().gnuFdl());
		return licenses;
	}
	
	public void setLicense(License license) {
		this.shortName = license.shortName;
		this.longName = license.longName;
		this.description = license.description;
		this.url = license.url;
		this.rdf = license.rdf;
		this.isCC = license.isCC;
		this.isCopyleft = license.isCopyleft;
		this.isDeprecated = license.isDeprecated;
		this.image = license.image;
		fireLicenseListeners();
	}
}
