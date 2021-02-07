class CreateScoreBoardResults < ActiveRecord::Migration
  def self.up
    create_table :score_board_results do |t|
      t.column :team_id,     :integer
      t.column :event_id,    :integer
      t.column :episode_num, :integer
      t.column :return,      :decimal, {:precision => 63, :scale => 30}
      t.column :steps,       :integer
      t.column :finished_at, :datetime
      t.column :created_at,  :datetime
    end    
  end

  def self.down
    drop_table :score_board_results
  end
end
