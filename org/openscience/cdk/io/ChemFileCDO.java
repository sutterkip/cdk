/*
 * $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 * 
 * Copyright (C) 1997-2000  The CompChem project
 * 
 * Contact: steinbeck@ice.mpg.de, geelter@maul.chem.nd.edu, egonw@sci.kun.nl
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA. 
 */
package org.openscience.cdk.io;

import org.openscience.cdk.*;
import org.openscience.cml.*;
import org.openscience.cdopi.*;
import java.util.*;

/**
 * CDO object needed as interface with the JCFL library for reading CML
 * encoded data.
 */ 
public class ChemFileCDO extends ChemFile implements CDOInterface {

    private Molecule currentMolecule;
    private SetOfMolecules currentSetOfMolecules;
    private ChemModel currentChemModel;
    private ChemSequence currentChemSequence;
    private Atom currentAtom;

    private Hashtable atomEnumeration;

    private int numberOfAtoms = 0;

    private int bond_a1;
    private int bond_a2;
    private int bond_order;
    private int bond_stereo;

    /**
     * Basic contructor
     */
    public ChemFileCDO() {
	currentChemSequence = new ChemSequence();
	currentChemModel = new ChemModel();
	currentSetOfMolecules = new SetOfMolecules();
	atomEnumeration = new Hashtable();
    }
    
    // procedures required by CDOInterface

    /**
     * Procedure required by the CDOInterface. This function is only
     * supposed to be called by the JCFL library
     */
    public void startDocument() {
	System.out.println("New Document");
	currentChemSequence = new ChemSequence();
	currentChemModel = new ChemModel();
	currentSetOfMolecules = new SetOfMolecules();
	atomEnumeration = new Hashtable();
    };

    /**
     * Procedure required by the CDOInterface. This function is only
     * supposed to be called by the JCFL library
     */
    public void endDocument() {
	    currentSetOfMolecules.addMolecule(currentMolecule);	    
	    currentChemModel.setSetOfMolecules(currentSetOfMolecules);
	    currentChemSequence.addChemModel(currentChemModel);
	    this.addChemSequence(currentChemSequence);
	    System.out.println("Molecule added");
    };

    /**
     * Procedure required by the CDOInterface. This function is only
     * supposed to be called by the JCFL library
     */
    public void setDocumentProperty(String type, String value) {};
    
    /**
     * Procedure required by the CDOInterface. This function is only
     * supposed to be called by the JCFL library
     */
    public void startObject(String objectType) {
	System.out.println("CDOStartObject");
	if (objectType.equals("Molecule")) {
	    currentMolecule = new Molecule();
	} else if (objectType.equals("Atom")) {
	    currentAtom = new Atom("H");
	    numberOfAtoms++;
	} else if (objectType.equals("Bond")) {
	}
    };

    /**
     * Procedure required by the CDOInterface. This function is only
     * supposed to be called by the JCFL library
     */
    public void endObject(String objectType) {
	System.out.println("END: " + objectType);
	if (objectType.equals("Molecule")) {
	    currentSetOfMolecules.addMolecule(currentMolecule);
	    currentChemModel.setSetOfMolecules(currentSetOfMolecules);
	    currentChemSequence.addChemModel(currentChemModel);
	    addChemSequence(currentChemSequence);
	    System.out.println("This file has " + getChemSequenceCount() + " sequences.");
	    System.out.println("Molecule added: \n" + currentMolecule.toString());
	} else if (objectType.equals("Atom")) {
	    currentMolecule.addAtom(currentAtom);
	} else if (objectType.equals("Bond")) {
	    System.out.println("Bond: " + bond_a1 + " " + bond_a2 + " " + bond_order);
	    currentMolecule.addBond(bond_a1, bond_a2, bond_order);
	}
    };
    
    /**
     * Procedure required by the CDOInterface. This function is only
     * supposed to be called by the JCFL library
     */
    public void setObjectProperty(String objectType, String propertyType,
				  String propertyValue) {
	System.out.println("objectType: " + objectType);
	System.out.println("propType: " + propertyType);
	System.out.println("property: " + propertyValue);
	if (objectType.equals("Atom")) {
	    if (propertyType.equals("type")) {
			currentAtom.setElement(new Element(propertyValue));
	    } else if (propertyType.equals("x2")) {
			Double value = new Double(propertyValue);
            double val = value.doubleValue();
			System.out.println(val);
			System.out.println(currentAtom);
			currentAtom.setX2D(val);
			System.out.println("Done");
	    } else if (propertyType.equals("y2")) {
			currentAtom.setY2D(new Double(propertyValue).doubleValue());
	    } else if (propertyType.equals("id")) {
			System.out.println("id" + propertyValue);
			atomEnumeration.put(propertyValue, new Integer(numberOfAtoms));
	    }
	} else if (objectType.equals("Bond")) {
	    if (propertyType.equals("atom1")) {
		bond_a1 = new Integer(propertyValue).intValue();
	    } else if (propertyType.equals("atom2")) {
		bond_a2 = new Integer(propertyValue).intValue();
	    } else if (propertyType.equals("order")) {
		bond_order = java.lang.Integer.valueOf(propertyValue).intValue();
	    }
	}
	System.out.println("Set...");
    };
    
    /**
     * Procedure required by the CDOInterface. This function is only
     * supposed to be called by the JCFL library
     */
    public CDOAcceptedObjects acceptObjects() {
	CDOAcceptedObjects objects = new CDOAcceptedObjects();
	objects.add("Molecule");
	objects.add("Fragment");
	objects.add("Atom");
	objects.add("Bond");
	return objects;      
    };
}

