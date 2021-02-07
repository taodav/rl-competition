class AddExtensionAndNumStepsToEvents < ActiveRecord::Migration
  def self.up
    add_column :events, :extension, :string
    add_column :events, :num_steps, :integer
  end

  def self.down
    remove_column :events, :extension
    remove_column :events, :num_steps
  end
end
