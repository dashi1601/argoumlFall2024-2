/* $Id$
 *****************************************************************************
 * Copyright (c) 2009 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    tfmorris
 *****************************************************************************
 *
 * Some portions of this file was previously release using the BSD License:
 */

// Copyright (c) 1996-2007 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

package org.argouml.cognitive.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;

import org.argouml.cognitive.LayoutHelper;
import org.argouml.cognitive.critics.Wizard;
import org.argouml.swingext.SpacerPanel;


/**
 * A non-modal wizard step that shows instructions and allows
 * the user to select one of a series of radio-buttons.
 *
 * @see org.argouml.cognitive.Critic
 * @see org.argouml.cognitive.critics.Wizard
 */

public class WizStepChoice extends WizStep {
	private JTextArea instructions = new JTextArea();
	private List<String> choices = new ArrayList<String>();
	private int selectedIndex = -1;

	/**
	 * The constructor.
	 *
	 * @param w the wizard
	 * @param instr the instructions
	 * @param ch the choices
	 */
	public WizStepChoice(Wizard w, String instr, List<String> ch) {
		// Store a defensive copy of the input list
		this.choices = new ArrayList<>(ch);

		instructions.setText(instr);
		instructions.setWrapStyleWord(true);
		instructions.setEditable(false);
		instructions.setBorder(null);
		instructions.setBackground(getMainPanel().getBackground());

		getMainPanel().setBorder(new EtchedBorder());

		GridBagLayout gb = new GridBagLayout();
		getMainPanel().setLayout(gb);

		GridBagConstraints c = new GridBagConstraints();
		c.ipadx = 3;
		c.ipady = 3;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.anchor = GridBagConstraints.EAST;

		JLabel image = new JLabel("");
		image.setIcon(getWizardIcon());
		image.setBorder(null);

		LayoutHelper.addComponent(getMainPanel(), image, gb, c, 0, 0, GridBagConstraints.REMAINDER, 1, 1.0, GridBagConstraints.NORTH);

		// Use LayoutHelper to add instructions
		LayoutHelper.addComponent(getMainPanel(), instructions, gb, c, 2, 0, 3, 1, 1.0, GridBagConstraints.HORIZONTAL);

		// Use LayoutHelper to add spacer
		SpacerPanel spacer = new SpacerPanel();
		LayoutHelper.addComponent(getMainPanel(), spacer, gb, c, 1, 1, 1, 1, 0.0, GridBagConstraints.NONE);

		c.gridx = 2;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.WEST;
		c.gridwidth = 1;
		int size = this.choices.size();
		for (int i = 0; i < size; i++) {
			c.gridy = 2 + i;
			String s = this.choices.get(i);
			JRadioButton rb = new JRadioButton(s);
			rb.setActionCommand(s);
			rb.addActionListener(this);
			gb.setConstraints(rb, c);
			getMainPanel().add(rb);
		}

		c.gridx = 1;
		c.gridy = 3 + this.choices.size();
		c.weightx = 0.0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
		SpacerPanel spacer2 = new SpacerPanel();
		gb.setConstraints(spacer2, c);
		getMainPanel().add(spacer2);
	}

	/**
	 * @return the index of the selected item
	 */
	public int getSelectedIndex() {
		return selectedIndex;
	}

	/*
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		super.actionPerformed(e);
		if (e.getSource() instanceof JRadioButton) {
			String cmd = e.getActionCommand();
			if (cmd == null) {
				selectedIndex = -1;
				return;
			}
			int size = choices.size();
			for (int i = 0; i < size; i++) {
				String s = choices.get(i);
				if (s.equals(cmd)) {
					selectedIndex = i;
				}
			}
			getWizard().doAction();
			enableButtons();
		}
	}

	/**
	 * The UID.
	 */
	private static final long serialVersionUID = 8055896491830976354L;
}




