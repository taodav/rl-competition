class RenameScoreBoardResultsScoreColumn < ActiveRecord::Migration
  def self.up
    add_column :score_board_results, :score, :decimal, {:precision => 63, :scale => 30}
    remove_column :score_board_results, :return
  end
  def self.down
    remove_column :score_board_results, :score
    add_column :score_board_results, :return, :decimal, {:precision => 63, :scale => 30}
  end
end
