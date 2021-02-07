class AddScoreToMaxScores < ActiveRecord::Migration
  def self.up
    add_column :max_scores, :score, :float
  end

  def self.down
    remove_column :max_scores, :score
  end
end
