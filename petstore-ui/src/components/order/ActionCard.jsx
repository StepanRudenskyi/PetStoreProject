import React from "react";
import "../../styles/components/order/ActionCard.css";

const ActionCard = ({
  icon: Icon,
  title,
  description,
  iconBgClass,
  iconColor,
}) => (
  <div className="action-card">
    <div className={`action-card__icon-wrapper ${iconBgClass}`}>
      <Icon size={24} color={iconColor} />
    </div>
    <div className="action-card__info">
      <h3 className="action-card__title">{title}</h3>
      <p className="action-card__description">{description}</p>
    </div>
  </div>
);

export default ActionCard;
