/* $RCSfile$
 * $Author$    
 * $Date$    
 * $Revision$
 * 
 * Copyright (C) 2003-2007  The Chemistry Development Kit (CDK) project
 * 
 * Contact: cdk-devel@lists.sourceforge.net
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
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA. 
 * 
 */
package org.openscience.cdk.interfaces;

import javax.vecmath.Point2d;
import javax.vecmath.Point3d;

import org.junit.Assert;
import org.junit.Test;

/**
 * Checks the functionality of {@link IPseudoAtom} implementations.
 *
 * @cdk.module test-interfaces
 */
public abstract class AbstractPseudoAtomTest extends AbstractAtomTest {

    @Test public void testGetLabel() {
        String label = "Arg255";
        IPseudoAtom a = (IPseudoAtom)newChemObject();
        a.setLabel(label);
        Assert.assertEquals(label, a.getLabel());
    }

    @Test public void testSetLabel_String() {
        String label = "Arg255";
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        atom.setLabel(label);
        String label2 = "His66";
        atom.setLabel(label2);
        Assert.assertEquals(label2, atom.getLabel());
    }

    @Test public void testGetFormalCharge() {
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        Assert.assertEquals(0, atom.getFormalCharge().intValue());
    }

    @Test public void testSetFormalCharge_Integer() {
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        atom.setFormalCharge(+5);
        Assert.assertEquals(+5, atom.getFormalCharge().intValue());
    }

    @Test public void testSetHydrogenCount_Integer() {
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        atom.setHydrogenCount(+5);
        Assert.assertEquals(0, atom.getHydrogenCount().intValue());
    }

    @Test public void testSetCharge_Double() {
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        atom.setCharge(0.78);
        Assert.assertEquals(0.78, atom.getCharge(), 0.001);
    }

    @Test public void testSetExactMass_Double() {
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        atom.setExactMass(12.001);
        Assert.assertEquals(12.001, atom.getExactMass(), 0.001);
    }

    @Test public void testSetStereoParity_Integer() {
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        atom.setStereoParity(-1);
        Assert.assertEquals(0, atom.getStereoParity().intValue());
    }

    @Test public void testPseudoAtom_IAtom() {
    	IChemObject object = newChemObject();
        IAtom atom = object.getBuilder().newAtom("C");
        Point3d fract = new Point3d(0.5, 0.5, 0.5);
        Point3d threeD = new Point3d(0.5, 0.5, 0.5);
        Point2d twoD = new Point2d(0.5, 0.5);
        atom.setFractionalPoint3d(fract);
        atom.setPoint3d(threeD);
        atom.setPoint2d(twoD);
        
        IPseudoAtom a = object.getBuilder().newPseudoAtom(atom);
        assertEquals(fract, a.getFractionalPoint3d(), 0.0001);
        assertEquals(threeD, a.getPoint3d(), 0.0001);
        assertEquals(twoD, a.getPoint2d(), 0.0001);
    }

    /**
     * Method to test the clone() method
     */
    @Test public void testClone() throws Exception {
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        Object clone = atom.clone();
        Assert.assertTrue(clone instanceof IPseudoAtom);
    }
    
    /**
     * Method to test whether the class complies with RFC #9.
     */
    @Test public void testToString() {
        IAtom atom = (IPseudoAtom)newChemObject();
        String description = atom.toString();
        for (int i=0; i< description.length(); i++) {
            Assert.assertTrue(description.charAt(i) != '\n');
            Assert.assertTrue(description.charAt(i) != '\r');
        }
    }
    
    /**
     * Test for bug #1778479 "MDLWriter writes empty PseudoAtom label string".
     * We decided to let the pseudo atoms have a default label of '*'.
     *
     * Author: Andreas Schueller <a.schueller@chemie.uni-frankfurt.de>
     * 
     * @cdk.bug 1778479
     */
    @Test public void testBug1778479DefaultLabel() {
        IPseudoAtom atom = (IPseudoAtom)newChemObject();
        Assert.assertNotNull("Test for PseudoAtom's default label", atom.getLabel());
        Assert.assertEquals("Test for PseudoAtom's default label", "*", atom.getLabel());
    }

    /**
     * Overwrite the method in {@link AbstractAtomTest} to always
     * expect zero hydrogen counts.
     */
    @Test public void testClone_HydrogenCount() throws Exception {
        IAtom atom = (IAtom)newChemObject();
        atom.setHydrogenCount(Integer.valueOf(3));
        IAtom clone = (IAtom)atom.clone();

        // test cloning
        atom.setHydrogenCount(Integer.valueOf(4));
        Assert.assertEquals(0, clone.getHydrogenCount().intValue());
    }

    /**
     * Overwrite the method in {@link AbstractAtomTest} to always
     * expect zero hydrogen counts.
     */
    @Test public void testGetHydrogenCount() {
        // expect zero by definition
        IAtom a = (IAtom)newChemObject();
        Assert.assertEquals(0, a.getHydrogenCount().intValue());
        a.setHydrogenCount(5);
        Assert.assertEquals(0, a.getHydrogenCount().intValue());
        a.setHydrogenCount(null);
        Assert.assertEquals(0, a.getHydrogenCount().intValue());
    }

    /**
     * Overwrite the method in {@link AbstractAtomTypeTest} to always
     * expect zero stereo parity.
     */
    @Test public void testClone_StereoParity() throws Exception {
        IAtom atom = (IAtom)newChemObject();
        atom.setStereoParity(3);
        IAtom clone = (IAtom)atom.clone();

        // test cloning
        atom.setStereoParity(4);
        Assert.assertEquals(0, clone.getStereoParity().intValue());
    }


    @Test public void testPseudoAtomCharges() {
        String label = "charged patom";
        IPseudoAtom a = (IPseudoAtom)newChemObject();
        a.setLabel(label);
        a.setFormalCharge(-1);
        Assert.assertNotNull(a);
        Assert.assertNotNull(a.getFormalCharge());
        Assert.assertEquals(-1, a.getFormalCharge().intValue());
    }
}
