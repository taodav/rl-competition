class AddLogFrequencyToEvents < ActiveRecord::Migration
  def self.up
    add_column :events, :log_frequency, :integer, :default => 1
  end

  def self.down
    remove_column :events, :log_frequency
  end
end
