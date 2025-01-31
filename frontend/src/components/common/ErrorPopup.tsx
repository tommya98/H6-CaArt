import React, { useEffect } from 'react';
import styled from 'styled-components';
import { Link } from 'react-router-dom';
import SquareButton from './SquareButton';

function ErrorPopup() {
  useEffect(() => {
    document.body.style.cssText = `
      background: rgba(15,17,20,0.55);`;
    return () => {
      document.body.style.cssText = ``;
    };
  }, []);

  return (
    <ErrorPopupBox>
      <span className="head-medium-22 text-grey-50">
        내 차 만들기 과정 중 <br />
        오류가 발생했어요
      </span>
      <ErrorPopupImg src="/images/logo/error_logo_blue.svg"></ErrorPopupImg>
      <Link to="/">
        <SquareButton size="l" color="grey-1000" $bg="primary-blue">
          확인
        </SquareButton>
      </Link>
    </ErrorPopupBox>
  );
}

const ErrorPopupBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: center;
  width: 427px;
  height: 392px;
  padding: 24px 32px 32px 32px;
  border-radius: 12px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: var(--grey-1000);
`;

const ErrorPopupImg = styled.img`
  width: 222px;
  height: 138px;
  margin-top: 48px;
  margin-bottom: 48px;
  transform: translateX(70px);
`;

export default ErrorPopup;
