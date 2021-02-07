//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.02 at 05:14:34 PM EDT 
//


package org.rlcommunity.environment.octopus.config;

import java.math.BigInteger;
import org.rlcommunity.environment.octopus.config.TaskDef;


/**
 * <p>Java class for TaskDef complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaskDef">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="stepReward" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="timeLimit" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

public abstract class TaskDef {

    protected Double stepReward;
    protected BigInteger timeLimit;

    public TaskDef(BigInteger timeLimit, Double stepReward){
        this.timeLimit=timeLimit;
        this.stepReward=stepReward;
    }
    /**
     * Gets the value of the stepReward property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getStepReward() {
        return stepReward;
    }

    /**
     * Sets the value of the stepReward property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setStepReward(Double value) {
        this.stepReward = value;
    }

    /**
     * Gets the value of the timeLimit property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTimeLimit() {
        return timeLimit;
    }

    /**
     * Sets the value of the timeLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTimeLimit(BigInteger value) {
        this.timeLimit = value;
    }

}
