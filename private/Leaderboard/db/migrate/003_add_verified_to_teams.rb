class AddVerifiedToTeams < ActiveRecord::Migration
  def self.up
    add_column :teams, :verified, :boolean
  end

  def self.down
    remove_column :teams, :verified
  end
end
