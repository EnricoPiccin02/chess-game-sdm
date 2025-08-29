package com.sdm.units.chessgame.gamecontrol.assembler.domain;

import com.sdm.units.chessgame.gamecontrol.flow.ChessGameEventFactory;
import com.sdm.units.chessgame.gamecontrol.flow.EventFactory;
import com.sdm.units.chessgame.gamecontrol.flow.ScoreKeeper;
import com.sdm.units.chessgame.gamecontrol.flow.TurnManager;
import com.sdm.units.chessgame.gamecontrol.state.BoardAssembly;
import com.sdm.units.chessgame.gamecontrol.state.GameStateAssembly;
import com.sdm.units.chessgame.gamelogic.board.evaluation.EvaluatorsFactory;
import com.sdm.units.chessgame.gamelogic.board.evaluation.GameOutcomeEvaluator;
import com.sdm.units.chessgame.gamelogic.board.state.BoardStateManager;
import com.sdm.units.chessgame.gamelogic.board.state.Chessboard;
import com.sdm.units.chessgame.gamelogic.board.state.DefaultLegalMoveFinder;
import com.sdm.units.chessgame.gamelogic.board.state.LegalMoveFinder;
import com.sdm.units.chessgame.gamelogic.board.state.MoveExecutor;
import com.sdm.units.chessgame.gamelogic.board.state.MoveExecutorService;
import com.sdm.units.chessgame.gamelogic.board.state.MoveHistory;
import com.sdm.units.chessgame.gamelogic.board.state.MoveRecorder;
import com.sdm.units.chessgame.gamelogic.domain.ChessboardOrientation;
import com.sdm.units.chessgame.gamelogic.initialization.ChessboardSetup;
import com.sdm.units.chessgame.gamelogic.initialization.StandardChessPiecePlacement;
import com.sdm.units.chessgame.gamelogic.move.SingleRuleFactory;
import com.sdm.units.chessgame.gamelogic.move.core.MoveRuleSet;
import com.sdm.units.chessgame.gamelogic.move.core.ReversibleMove;
import com.sdm.units.chessgame.gamelogic.move.special.promotion.PromotionPieceSelector;

public final class ChessDomainFactory {

    private final ChessboardOrientation orientation;
    private final PromotionPieceSelector selector;
    private final Chessboard board;

    public ChessDomainFactory(ChessboardOrientation orientation, PromotionPieceSelector selector) {
        this.orientation = orientation;
        this.selector = selector;
        this.board = new BoardStateManager(orientation, new ChessboardSetup(new StandardChessPiecePlacement()));
    }

    public BoardAssembly createChessboardComponents() {
        MoveRecorder<ReversibleMove> recorder = new MoveHistory();
        
        EvaluatorsFactory evaluatorsFactory = new EvaluatorsFactory(orientation);
        SingleRuleFactory ruleFactory = new SingleRuleFactory(recorder, selector);
        MoveRuleSet rules = new RulesFactory(ruleFactory, evaluatorsFactory).createCompleteRules();

        MoveExecutor executor = new MoveExecutorService(recorder);
        LegalMoveFinder moveFinder = new DefaultLegalMoveFinder(rules.asValidator(), rules.asGenerator(), orientation);
        GameOutcomeEvaluator outcome = evaluatorsFactory.gameOutcomeEvaluator(rules);
        
        return new BoardAssembly(board, executor, moveFinder, outcome);
    }

    public GameStateAssembly createGameStateComponents() {
        TurnManager turns = new TurnManager();
        ScoreKeeper scores = new ScoreKeeper();
        EventFactory notifier = new ChessGameEventFactory(board);
        
        return new GameStateAssembly(turns, scores, notifier);
    }
}