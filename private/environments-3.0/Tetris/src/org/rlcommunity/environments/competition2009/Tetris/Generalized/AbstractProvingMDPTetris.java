// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractProvingMDPTetris.java

package org.rlcommunity.environments.competition2009.Tetris.Generalized;

import org.rlcommunity.environments.competition2009.Tetris.Tetris;

public abstract class AbstractProvingMDPTetris extends Tetris
{

    public AbstractProvingMDPTetris()
    {
        super.recordLogs = true;
        super.allowSaveLoadState = false;
        super.allowSaveLoadSeed = false;
    }
}
