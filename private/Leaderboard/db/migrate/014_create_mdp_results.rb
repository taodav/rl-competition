class CreateMdpResults < ActiveRecord::Migration
  def self.up
    create_table :mdp_results do |t|
      t.column :team_id,      :integer
      t.column :event_id,     :integer
      t.column :mdp_id,       :integer
      t.column :episodes,     :integer
      t.column :total_return, :float
      t.column :total_steps,  :integer
    end
  end

  def self.down
    drop_table :mdp_results
  end
end
