/* $RCSfile$
 * $Author$
 * $Date$
 * $Revision$
 *
 * Copyright (C) 1997-2002  The Chemistry Development Kit (CDK) project
 * 
 * Contact: steinbeck@ice.mpg.de, gezelter@maul.chem.nd.edu, egonw@sci.kun.nl
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 * All we ask is that proper credit is given for our work, which includes
 * - but is not limited to - adding the above copyright notice to the beginning
 * of your source code files, and to any copyright notice that you may distribute
 * with programs based on this work.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *  */
package org.openscience.cdk.test.io.cml;

import org.openscience.cdk.*;
import org.openscience.cdk.io.*;
import org.openscience.cdk.geometry.*;
import java.io.*;
import junit.framework.*;
import com.baysmith.io.FileUtilities;
import java.util.Iterator;

/**
 * TestCase for the reading CML files using a few test files
 * in data/cmltest as found in the JChemPaint distribution
 * (http://jchempaint.sf.org/).
 */
public class JChemPaintTest extends TestCase {

    private org.openscience.cdk.tools.LoggingTool logger;

    public JChemPaintTest(String name) {
        super(name);
        logger = new org.openscience.cdk.tools.LoggingTool(this.getClass().getName());
    }

    public static Test suite() {
        return new TestSuite(JChemPaintTest.class);
    }

    /**
     * This one tests a CML2 file.
     */
    public void testSalt() {
        String filename = "data/cmltest/COONa.cml";
        logger.info("Testing: " + filename);
        InputStream ins = this.getClass().getClassLoader().getResourceAsStream(filename);
        try {
            CMLReader reader = new CMLReader(new InputStreamReader(ins));
            ChemFile chemFile = (ChemFile)reader.read((ChemObject)new ChemFile());

            // test the resulting ChemFile content
            assertNotNull(chemFile);
            assertEquals(1, chemFile.getChemSequenceCount());
            //System.out.println("NO sequences: " + chemFile.getChemSequenceCount());
            ChemSequence seq = chemFile.getChemSequence(0);
            assertNotNull(seq);
            assertEquals(1, seq.getChemModelCount());
            //System.out.println("NO models: " + seq.getChemModelCount());
            ChemModel model = seq.getChemModel(0);
            assertNotNull(model);
            assertEquals(1, model.getSetOfMolecules().getMoleculeCount());

            // test the molecule
            Molecule mol = model.getSetOfMolecules().getMolecule(0);
            assertNotNull(mol);
            assertEquals(4, mol.getAtomCount());
            assertTrue(GeometryTools.has3DCoordinates(mol));
        } catch (Exception e) {
            fail(e.toString());
        }
    }

}
