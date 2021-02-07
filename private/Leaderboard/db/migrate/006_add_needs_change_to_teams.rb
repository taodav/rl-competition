class AddNeedsChangeToTeams < ActiveRecord::Migration
  def self.up
    add_column :teams, :needs_change, :boolean
  end

  def self.down
    remove_column :teams, :needs_change
  end
end
