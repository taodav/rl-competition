//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.0-b26-ea3 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2006.05.02 at 05:14:34 PM EDT 
//


package Octopus.config;

import Octopus.config.ConstantSet;


/**
 * <p>Java class for ConstantSet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConstantSet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="frictionTangential" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="frictionPerpendicular" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="buoyancy" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="pressure" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="gravity" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="muscleActive" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="musclePassive" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="muscleNormalizedMinLength" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="muscleDamping" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="surfaceLevel" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="repulsionConstant" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="repulsionPower" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="repulsionThreshold" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */

public class ConstantSet {

    protected double frictionTangential;
    protected double frictionPerpendicular;
    protected double buoyancy;
    protected double pressure;
    protected double gravity;
    protected double muscleActive;
    protected double musclePassive;
    protected double muscleNormalizedMinLength;
    protected double muscleDamping;
    protected double surfaceLevel;
    protected double repulsionConstant;
    protected double repulsionPower;
    protected double repulsionThreshold;
    protected double torqueCoefficient;
    
    public ConstantSet(){
        //Set some defaults from the xml file
        this.frictionTangential=0.4d;
        this.frictionPerpendicular=1.0d;
        this.buoyancy=0.08d;
        this.pressure=10.0d;
        this.gravity=0.01d;
        this.muscleActive=0.1d;
        this.musclePassive=0.04d;
        this.muscleNormalizedMinLength=0.1d;
        this.muscleDamping=-1.0d;
        this.surfaceLevel=5.0d;
        this.repulsionConstant=0.01d;
        this.repulsionPower=1.0d;
        this.repulsionThreshold=0.7d;
        this.torqueCoefficient=0.025;
    }
    /**
   <frictionTangential>0.4</frictionTangential>
    <frictionPerpendicular>1</frictionPerpendicular>
    <pressure>10</pressure>
    <gravity>0.01</gravity>
    <surfaceLevel>5</surfaceLevel>
    <buoyancy>0.08</buoyancy>
    <muscleActive>0.1</muscleActive>
    <musclePassive>0.04</musclePassive>
    <muscleNormalizedMinLength>0.1</muscleNormalizedMinLength>
    <muscleDamping>-1.0</muscleDamping>
    <!-- the values 0.04 and 2.3 produce good behaviour for a 40-compartment arm -->
    <repulsionConstant>.01</repulsionConstant> 
    <repulsionPower>1.0</repulsionPower>
    <repulsionThreshold>.7</repulsionThreshold>
    <torqueCoefficient>0.025</torqueCoefficient>
     * 
     */
                
    /**
     * Gets the value of the frictionTangential property.
     * 
     */
    public double getFrictionTangential() {
        return frictionTangential;
    }

    public double getTorqueCoefficient() {
      return torqueCoefficient;
    }

    /**
     * Sets the value of the frictionTangential property.
     * 
     */
    public void setFrictionTangential(double value) {
        this.frictionTangential = value;
    }

    /**
     * Gets the value of the frictionPerpendicular property.
     * 
     */
    public double getFrictionPerpendicular() {
        return frictionPerpendicular;
    }

    /**
     * Sets the value of the frictionPerpendicular property.
     * 
     */
    public void setFrictionPerpendicular(double value) {
        this.frictionPerpendicular = value;
    }

    /**
     * Gets the value of the buoyancy property.
     * 
     */
    public double getBuoyancy() {
        return buoyancy;
    }

    /**
     * Sets the value of the buoyancy property.
     * 
     */
    public void setBuoyancy(double value) {
        this.buoyancy = value;
    }

    /**
     * Gets the value of the pressure property.
     * 
     */
    public double getPressure() {
        return pressure;
    }

    /**
     * Sets the value of the pressure property.
     * 
     */
    public void setPressure(double value) {
        this.pressure = value;
    }

    /**
     * Gets the value of the gravity property.
     * 
     */
    public double getGravity() {
        return gravity;
    }

    /**
     * Sets the value of the gravity property.
     * 
     */
    public void setGravity(double value) {
        this.gravity = value;
    }

    /**
     * Gets the value of the muscleActive property.
     * 
     */
    public double getMuscleActive() {
        return muscleActive;
    }

    /**
     * Sets the value of the muscleActive property.
     * 
     */
    public void setMuscleActive(double value) {
        this.muscleActive = value;
    }

    /**
     * Gets the value of the musclePassive property.
     * 
     */
    public double getMusclePassive() {
        return musclePassive;
    }

    /**
     * Sets the value of the musclePassive property.
     * 
     */
    public void setMusclePassive(double value) {
        this.musclePassive = value;
    }

    /**
     * Gets the value of the muscleNormalizedMinLength property.
     * 
     */
    public double getMuscleNormalizedMinLength() {
        return muscleNormalizedMinLength;
    }

    /**
     * Sets the value of the muscleNormalizedMinLength property.
     * 
     */
    public void setMuscleNormalizedMinLength(double value) {
        this.muscleNormalizedMinLength = value;
    }

    /**
     * Gets the value of the muscleDamping property.
     * 
     */
    public double getMuscleDamping() {
        return muscleDamping;
    }

    /**
     * Sets the value of the muscleDamping property.
     * 
     */
    public void setMuscleDamping(double value) {
        this.muscleDamping = value;
    }

    /**
     * Gets the value of the surfaceLevel property.
     * 
     */
    public double getSurfaceLevel() {
        return surfaceLevel;
    }

    /**
     * Sets the value of the surfaceLevel property.
     * 
     */
    public void setSurfaceLevel(double value) {
        this.surfaceLevel = value;
    }

    /**
     * Gets the value of the repulsionConstant property.
     * 
     */
    public double getRepulsionConstant() {
        return repulsionConstant;
    }

    /**
     * Sets the value of the repulsionConstant property.
     * 
     */
    public void setRepulsionConstant(double value) {
        this.repulsionConstant = value;
    }

    /**
     * Gets the value of the repulsionPower property.
     * 
     */
    public double getRepulsionPower() {
        return repulsionPower;
    }

    /**
     * Sets the value of the repulsionPower property.
     * 
     */
    public void setRepulsionPower(double value) {
        this.repulsionPower = value;
    }

    /**
     * Gets the value of the repulsionThreshold property.
     * 
     */
    public double getRepulsionThreshold() {
        return repulsionThreshold;
    }

    /**
     * Sets the value of the repulsionThreshold property.
     * 
     */
    public void setRepulsionThreshold(double value) {
        this.repulsionThreshold = value;
    }

}
