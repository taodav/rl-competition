class ChangeMdpResultToKey < ActiveRecord::Migration
  def self.up
    remove_column :mdp_results, :team_id
    remove_column :mdp_results, :event_id
    add_column :mdp_results, :team_key_id, :integer
  end

  def self.down
    add_column :mdp_results, :team_id,  :integer
    add_column :mdp_results, :event_id,     :integer
    remove_column :mdp_results, :team_key_id
  end
end
