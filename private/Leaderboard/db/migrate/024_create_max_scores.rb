class CreateMaxScores < ActiveRecord::Migration
  def self.up
    create_table :max_scores do |t|
      t.column :team_id, :integer
      t.column :event_id, :integer
      t.column :summary_result_id, :integer
    end
  end

  def self.down
    drop_table :max_scores
  end
end
